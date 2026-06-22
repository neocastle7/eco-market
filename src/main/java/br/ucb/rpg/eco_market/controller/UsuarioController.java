package br.ucb.rpg.eco_market.controller;

import br.ucb.rpg.eco_market.model.Usuario;
import br.ucb.rpg.eco_market.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Permite que o HTML do Luan converse com seu código sem travar
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Rota para criar novas contas
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        try {
            Usuario novo = usuarioService.cadastrar(usuario);
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Rota de Login que a tela do Luan vai consumir
    @PostMapping("/login")
    public ResponseEntity<?> logar(@RequestBody Map<String, String> dados) {
        try {
            String email = dados.get("email");
            String senha = dados.get("senha");
            Usuario usuarioLogado = usuarioService.login(email, senha);
            return ResponseEntity.ok(usuarioLogado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
