package br.ucb.rpg.eco_market.Repository;

import br.ucb.rpg.eco_market.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // TODO (Lucas e Jackson): Criar métodos de busca customizados se o professor pedir na apresentação.
}