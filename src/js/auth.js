/* ==================================================
   CONFIGURAÇÕES
================================================== */
const API_URL = "http://localhost:8080/auth";

/* ==================================================
   TOASTS / ALERTAS
================================================== */
function showToast(texto, tipo = "success") {
    const toast = document.getElementById("authToast");
    const toastBody = document.getElementById("authToastBody");

    // Se o elemento toast ou o Bootstrap não carregou na página, usa o alert nativo para não travar o fluxo!
    if (!toast || !toastBody || typeof bootstrap === "undefined") {
        alert(texto);
        return;
    }

    toastBody.textContent = texto;
    toast.className = `toast align-items-center border-0 text-bg-${tipo}`;

    try {
        bootstrap.Toast.getOrCreateInstance(toast).show();
    } catch (e) {
        alert(texto);
    }
}

/* ==================================================
   VALIDAÇÕES
================================================== */
function emailLooksValid(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

/* ==================================================
   VISUALIZAÇÃO DE SENHA
================================================== */
function setupPasswordToggles() {
    document
        .querySelectorAll("[data-password-toggle]")
        .forEach((button) => {
            button.addEventListener("click", (e) => {
                e.preventDefault(); // Impede qualquer comportamento estranho
                const input = document.getElementById(button.dataset.passwordToggle);
                if (!input) return;

                const senhaVisivel = input.type === "text";
                input.type = senhaVisivel ? "password" : "text";
                button.textContent = senhaVisivel ? "Mostrar" : "Ocultar";
            });
        });
}

/* ==================================================
   LOGIN
================================================== */
function setupLogin() {
    const form = document.getElementById("loginForm");
    if (!form) return;

    configurarBotaoDemo(form);
    configurarEnvioLogin(form);
}

function configurarBotaoDemo(form) {
    const demoButton = document.querySelector("[data-demo-login]");
    if (!demoButton) return;

    demoButton.addEventListener("click", (e) => {
        e.preventDefault(); // FORÇA o navegador a não dar refresh!
        e.stopPropagation();

        const emailField = document.getElementById("email");
        const senhaField = document.getElementById("senha");

        if (emailField && senhaField) {
            emailField.value = "aluno@ucb.br";
            senhaField.value = "12345678";
            form.classList.remove("was-validated");
            showToast("Dados de teste preenchidos.", "success");
        }
    });
}

function configurarEnvioLogin(form) {
    form.addEventListener("submit", (event) => {
        event.preventDefault(); // TRAVA o refresh da página no envio do login

        const emailField = document.getElementById("email");
        const senhaField = document.getElementById("senha");

        if (!emailField || !senhaField) return;

        const email = emailField.value.trim();
        const senha = senhaField.value.trim();

        const loginValido = form.checkValidity() && emailLooksValid(email) && senha.length >= 6;

        if (!loginValido) {
            form.classList.add("was-validated");
            showToast("Revise os campos destacados antes de entrar.", "danger");
            return;
        }

        fetch(`${API_URL}/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, senha })
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(msg => { throw new Error(msg); });
            }
            return response.json();
        })
        .then(usuario => {
            showToast(`Bem-vindo, ${usuario.nome}!`, "success");
            setTimeout(() => {
                window.location.href = "cadastro-produto.html";
            }, 1000);
        })
        .catch(error => {
            showToast("Erro ao logar: " + error.message, "danger");
        });
    });
}

/* ==================================================
   CADASTRO
================================================== */
function setupCadastro() {
    const form = document.getElementById("cadastroForm");
    if (!form) return;

    const senhaInput = document.getElementById("senha");
    const confirmarSenhaInput = document.getElementById("confirmarSenha");
    const senhaAjuda = document.getElementById("senhaAjuda");
    const senhaForca = document.getElementById("senhaForca");

    if (senhaInput && confirmarSenhaInput) {
        senhaInput.addEventListener("input", () => {
            const score = calcularForcaSenha(senhaInput.value);
            atualizarForcaSenha(score, senhaForca, senhaAjuda);
            validarConfirmacaoSenha(senhaInput, confirmarSenhaInput);
        });

        confirmarSenhaInput.addEventListener("input", () => {
            validarConfirmacaoSenha(senhaInput, confirmarSenhaInput);
        });
    }

    form.addEventListener("submit", (event) => {
        event.preventDefault(); // TRAVA o refresh da página no envio do cadastro

        if (senhaInput && confirmarSenhaInput) {
            validarConfirmacaoSenha(senhaInput, confirmarSenhaInput);
        }

        if (!form.checkValidity()) {
            form.classList.add("was-validated");
            showToast("Revise os campos destacados antes de cadastrar.", "danger");
            return;
        }

        const emailField = document.getElementById("email");
        const nomeField = document.getElementById("nome");

        fetch(`${API_URL}/cadastro`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                nome: nomeField ? nomeField.value.trim() : "",
                email: emailField ? emailField.value.trim() : "",
                senha: senhaInput ? senhaInput.value : ""
            })
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(msg => { throw new Error(msg); });
            }
            return response.json();
        })
        .then(usuario => {
            showToast(`${usuario.nome} cadastrado com sucesso!`, "success");
            setTimeout(() => {
                window.location.href = "login.html";
            }, 1000);
        })
        .catch(error => {
            showToast("Erro ao cadastrar: " + error.message, "danger");
        });
    });
}

/* ==================================================
   FORÇA DA SENHA
================================================== */
function calcularForcaSenha(senha) {
    let score = 0;
    if (senha.length >= 8) score += 40;
    if (/[A-Z]/.test(senha)) score += 20;
    if (/[0-9]/.test(senha)) score += 20;
    if (/[^A-Za-z0-9]/.test(senha)) score += 20;
    return Math.min(score, 100);
}

function atualizarForcaSenha(score, progressBar, helpText) {
    if (!progressBar || !helpText) return;

    progressBar.style.width = `${score}%`;
    progressBar.textContent = `${score}%`;

    progressBar.classList.remove("bg-danger", "bg-warning", "bg-success");

    if (score < 50) {
        progressBar.classList.add("bg-danger");
        helpText.textContent = "Senha fraca: use pelo menos 8 caracteres.";
        return;
    }
    if (score < 80) {
        progressBar.classList.add("bg-warning");
        helpText.textContent = "Senha média: adicione números, letras maiúsculas ou símbolos.";
        return;
    }
    progressBar.classList.add("bg-success");
    helpText.textContent = "Senha forte.";
}

/* ==================================================
   CONFIRMAÇÃO DE SENHA
================================================== */
function validarConfirmacaoSenha(senhaInput, confirmarSenhaInput) {
    if (!confirmarSenhaInput.value) {
        confirmarSenhaInput.setCustomValidity("");
        return;
    }
    if (senhaInput.value !== confirmarSenhaInput.value) {
        confirmarSenhaInput.setCustomValidity("As senhas precisam ser iguais.");
        return;
    }
    confirmarSenhaInput.setCustomValidity("");
}

/* ==================================================
   INICIALIZAÇÃO
================================================== */
// Garante o carregamento seguro das funções principais
document.addEventListener("DOMContentLoaded", () => {
    setupPasswordToggles();
    setupLogin();
    setupCadastro();
});