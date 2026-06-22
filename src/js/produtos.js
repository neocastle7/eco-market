/* ==================================================
   CONFIGURAÇÕES
================================================== */
const API_URL = "http://localhost:8080/produtos";

/* ==================================================
   CADASTRO DE PRODUTO (POST)
================================================== */
const form = document.getElementById("formProduto");

if (form) {
    form.addEventListener("submit", function (e) {
        e.preventDefault();

        // Monta o objeto exatamente com as variáveis da sua classe Produto.java
        const produto = {
            nome: document.getElementById("nome").value.trim(),
            preco: parseFloat(document.getElementById("preco").value),
            estoque: parseInt(document.getElementById("estoque").value),
            pontosDeReciclagem: document.getElementById("pontos").value ? parseInt(document.getElementById("pontos").value) : null,
            creditosDeCarbono: document.getElementById("carbono").value ? parseFloat(document.getElementById("carbono").value) : null
        };

        // Dispara o envio real para o seu ProdutoController.java
        fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(produto)
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(msg => { throw new Error(msg); });
            }
            return response.json();
        })
        .then(dados => {
            alert("Produto cadastrado com sucesso no servidor!");
            form.reset();
        })
        .catch(error => {
            alert("Erro ao cadastrar: " + error.message);
        });
    });
}

/* ==================================================
   LISTAGEM DE PRODUTOS (GET)
================================================== */
const tabela = document.getElementById("tabelaProdutos");

if (tabela) {
    // Puxa os dados reais da sua API Java
    fetch(API_URL)
        .then(response => response.json())
        .then(produtos => {
            tabela.innerHTML = ""; // Limpa a tabela antes de renderizar
            produtos.forEach((produto) => {
                tabela.innerHTML += `
                    <tr>
                        <td>${produto.nome}</td>
                        <td>R$ ${produto.preco.toFixed(2)}</td>
                        <td>${produto.estoque}</td>
                        <td>${produto.pontosDeReciclagem || 0}</td>
                        <td>${produto.creditosDeCarbono || 0}</td>
                        <td>
                            <button
                                class="btn btn-danger btn-sm"
                                onclick="excluirProduto(${produto.id})">
                                Excluir
                            </button>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(error => {
            alert("Erro ao carregar produtos do servidor: " + error.message);
        });
}

/* ==================================================
   EXCLUSÃO DE PRODUTO (DELETE)
================================================== */
function excluirProduto(id) {
    if (!confirm("Tem certeza que deseja excluir este produto?")) {
        return;
    }

    // Chama o endpoint de Delete passando o ID do banco de dados
    fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(msg => { throw new Error(msg); });
        }
        alert("Produto removido com sucesso!");
        location.reload(); // Recarrega a página para atualizar a tabela
    })
    .catch(error => {
        alert("Erro ao excluir: " + error.message);
    });
}