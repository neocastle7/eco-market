package br.ucb.rpg.eco_market.Service;

import br.ucb.rpg.eco_market.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // TODO (Lucas e Jackson): Desenvolver as regras de negócio do CRUD de Produtos e tratamento de Exceções.
}