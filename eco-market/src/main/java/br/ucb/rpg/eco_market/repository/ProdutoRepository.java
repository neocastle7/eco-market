package br.ucb.rpg.eco_market.repository;

import br.ucb.rpg.eco_market.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // TODO (Lucas e Jackson): Criar métodos de busca customizados se o professor pedir na apresentação.

    // Exemplo de consulta customizada: buscar por nome
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    //Busca de produtos com estoque maior que 0
    List<Produto> findByEstoqueGreaterThan(Integer estoque);

}