package com.trabalho.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private Repository tarefaRepository;

    // Endpoint para criar uma nova tarefa
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaRepository.save(tarefa);
        return ResponseEntity.status(201).body(novaTarefa);
    }

    // Endpoint para listar todas as tarefas
    @GetMapping
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    // Endpoint para buscar uma tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Long id) {
        return tarefaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para atualizar uma tarefa
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        return tarefaRepository.findById(id)
                .map(tarefaExistente -> {
                    tarefaExistente.setNome(tarefaAtualizada.getNome());
                    tarefaExistente.setDataEntrega(tarefaAtualizada.getDataEntrega());
                    tarefaExistente.setResponsavel(tarefaAtualizada.getResponsavel());
                    Tarefa tarefaSalva = tarefaRepository.save(tarefaExistente);
                    return ResponseEntity.ok(tarefaSalva);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para deletar uma tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}