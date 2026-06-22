package br.ucb.rpg.eco_market.controller;

import br.ucb.rpg.eco_market.model.Produto;
import br.ucb.rpg.eco_market.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    //metodo cria produto chamando o metodo adicionarProduto, se der certo
    //o produto com  o status, se der erro, captura a exceçao e retorna um erro
    //404 com a mensagem
    @PostMapping
    public ResponseEntity<?> criarProduto(@RequestBody Produto produto) {
        try {
            // CORRIGIDO: De .salvar() para .adicionarProduto()
            Produto novoProduto = produtoService.adicionarProduto(produto);
            return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    /// chama o serviço que busca todos os produtos no banco e, depois, retorna
    /// a lista inteira dentro de uma resposta HTTP com status 200.
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }


    /// mesma coisa: utiliza o metodo buscaporid e se der certo
    /// retorna o status 202(ok) e o produto
    /// se der erro , a exceção captura e retorna o status do erro e a mensagem
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Produto produto = produtoService.buscarPorId(id);
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /// mesma coisa
    /// chama o metodo atualizaid (passando o id e o produto),
    /// e ser der bom retorna o status e o produto
    /// se nao, exceção dnv captura e retorna o status e a mensagem
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        try {
            Produto produto = produtoService.atualizar(id, produtoAtualizado);
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    ///  chama o metodo deletar passando id, se der certo retorna
    /// o status e a mensagem "Produto removid...." se der errado ,
    /// exceção retorna o status e a mensagem do erro.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            produtoService.deletar(id);
            return ResponseEntity.ok("Produto removido com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}