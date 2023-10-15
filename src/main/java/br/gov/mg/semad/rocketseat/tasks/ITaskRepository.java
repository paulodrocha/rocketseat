package br.gov.mg.semad.rocketseat.tasks;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ITaskRepository extends JpaRepository<TaskModel, UUID>{
    List<TaskModel> findByIdUser(UUID idUser);
    /* 
    outra forma de verificar permissão do usuário para alterar tarefas    
    TaskModel findByIdAndByIdUser(UUID id, UUID idUser);*/
}
