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

    // TODO (Lucas e Jackson): Desenvolver as regras de negócio do CRUD de Produtos e tratamento de Exceções.

    // Exemplo de lógica para calcular pontos de reciclagem


    //Metodos para adicionar um produto
    //nao permitindo produto com nome, estoque ou valor nulo
    public Produto adicionarProduto(Produto produto){

        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }

        if (produto.getPreco() == null || produto.getPreco() < 0) {
            throw new RuntimeException("Preço não pode ser negativo");
        }

        if (produto.getEstoque() == null || produto.getEstoque() < 0) {
            throw new RuntimeException("Estoque não pode ser negativo");
        }

        return produtoRepository.save(produto);
    }



    // Busca produtos pelo nome
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }


    // Busca produto por ID (ja padrão do JPA)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

    }

    // Metodo para alterar quantidade em estoque
    // nao permitindo quantd negativa;
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