package br.ucb.rpg.eco_market.service;

import br.ucb.rpg.eco_market.model.Produto;
import br.ucb.rpg.eco_market.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    ///  se a condicao nao for atendida o throw new vai interromper e lanca a exceção
    public Produto adicionarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }

        if (produto.getPreco() == null || produto.getPreco() < 0) {
            throw new RuntimeException("Preço não pode ser negativo");
        }

        if (produto.getEstoque() == null || produto.getEstoque() < 0) {
            throw new RuntimeException("Estoque não pode ser negativo");
        }

        if (produto.getPontosDeReciclagem() == null && produto.getPreco() != null) {
            produto.setPontosDeReciclagem((int) (produto.getPreco() * 10));
        }

        if (produto.getCreditosDeCarbono() == null) {
            produto.setCreditosDeCarbono(0.0);
        }

        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
    /// o orElseThrow ele verifica se ha valor existe e se nao, ele ja lanca
    /// a exceção automaticamente.

    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = buscarPorId(id);

        if (produtoAtualizado.getNome() == null || produtoAtualizado.getNome().isEmpty()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }
        if (produtoAtualizado.getPreco() == null || produtoAtualizado.getPreco() < 0) {
            throw new RuntimeException("Preço não pode ser negativo");
        }
        if (produtoAtualizado.getEstoque() == null || produtoAtualizado.getEstoque() < 0) {
            throw new RuntimeException("Estoque não pode ser negativo");
        }

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setEstoque(produtoAtualizado.getEstoque());
        produtoExistente.setPontosDeReciclagem(produtoAtualizado.getPontosDeReciclagem());
        produtoExistente.setCreditosDeCarbono(produtoAtualizado.getCreditosDeCarbono());

        return produtoRepository.save(produtoExistente);
    }

    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }

    public void alterarEstoque(Long id, int quantidade) {
        Produto produto = buscarPorId(id);
        int novoEstoque = produto.getEstoque() + quantidade;

        if (novoEstoque < 0) {
            throw new RuntimeException("Estoque não pode ficar negativo");
        }

        produto.setEstoque(novoEstoque);
        produtoRepository.save(produto);
    }
}