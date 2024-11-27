package br.edu.ifsp.bra.medsea.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.ifsp.bra.medsea.model.Depoimento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/depoimentos")
public class DepoimentoController {
    private List<Depoimento> historicoDepoimentos = new ArrayList<>();

    public DepoimentoController() {
        historicoDepoimentos.add(new Depoimento("14324546887", "Doação de medula",
                "Aprendi que doar medula óssea é mais simples do que imaginava. É incrível saber que podemos salvar vidas com um gesto tão simples!"));
        historicoDepoimentos.add(new Depoimento("14342342356", "Não sabia sobre a importância do tema", 
                "Antes, eu não sabia o quão importante era a compatibilidade genética para a doação de medula. Agora entendo como cada doador faz a diferença."));
        historicoDepoimentos.add(new Depoimento("67645453453", "Aprendizado sobre medula óssea",
                "Achei muito interessante descobrir que o processo de doação não causa grandes dores e que os bancos de medula são fundamentais para salvar vidas!"));
    }

    // Método GET para obter todos os depoimentos
    @GetMapping
    public List<Depoimento> getAllDepoimentos() {
        return historicoDepoimentos;
    }

    // Método GET para obter um depoimento específico pelo CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<Depoimento> getDepoimentoByCpf(@PathVariable String cpf) {
        Optional<Depoimento> depoimento = historicoDepoimentos.stream()
                .filter(d -> d.getUsuarioCpf().equals(cpf))
                .findFirst();

        return depoimento.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método POST para criar um novo depoimento
    @PostMapping
    public Depoimento createNewDepoimento(@RequestBody Depoimento newDepoimento) {
        historicoDepoimentos.add(newDepoimento);
        return newDepoimento;
    }

    // Método PUT para atualizar um depoimento existente pelo CPF
    @PutMapping("/{cpf}")
    public ResponseEntity<Depoimento> updateDepoimento(@PathVariable String cpf,
            @RequestBody Depoimento depoimentoAtualizado) {
        Optional<Depoimento> depoimentoExistente = historicoDepoimentos.stream()
                .filter(d -> d.getUsuarioCpf().equals(cpf))
                .findFirst();

        if (depoimentoExistente.isPresent()) {
            Depoimento depoimento = depoimentoExistente.get();
            depoimento.setTituloDepoimento(depoimentoAtualizado.getTituloDepoimento());
            depoimento.setConteudoDepoimento(depoimentoAtualizado.getConteudoDepoimento());
            return ResponseEntity.ok(depoimento);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Método DELETE para remover um depoimento pelo CPF
    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deleteDepoimento(@PathVariable String cpf) {
        Optional<Depoimento> depoimentoToRemove = historicoDepoimentos.stream()
                .filter(d -> d.getUsuarioCpf().equals(cpf))
                .findFirst();

        if (depoimentoToRemove.isPresent()) {
            historicoDepoimentos.remove(depoimentoToRemove.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Depoimento deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Depoimento não encontrado.");
        }
    }
}
