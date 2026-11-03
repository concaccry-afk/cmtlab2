package controller;

import constant.Message;
import dto.ResponseDTO;
import dto.TaskDTO;
import java.util.ArrayList;
import java.util.List;
import repository.TaskRepository;
import view.TaskView;

public class Controller {

    private TaskRepository taskRepository; // Gọi trực tiếp Repository
    private TaskDTO taskDto;
    private TaskView taskView;

    public Controller() {
        this.taskRepository = new TaskRepository();
        this.taskDto = new TaskDTO();
        this.taskView = new TaskView();
    }

    public TaskView getView() {
        return taskView;
    }

    public TaskDTO getDto() {
        return taskDto;
    }

    public void addTask() {
        // thuc hien them va kiem tra ket qua tra ve
        int generatedId = taskRepository.addTask(taskDto);
        if (generatedId > 0) {
            taskView.printMessage(Message.SUCCESS_ADD_MESSAGE);
        } else {
            taskView.printMessage(Message.ERROR_CANNOT_ADD);
        }
    }

    public void deleteTask() {
        // Logic kiem tra ID ton tai truoc khi xoa (Thay cho Service)
        if (taskRepository.findById(taskDto.getId()) == null) {
            taskView.printMessage(Message.ERROR_CANNOT_FOUND);
            return;
        }

        if (taskRepository.deleteTask(taskDto.getId())) {
            taskView.printMessage(Message.SUCCESS_DELETE_MESSAGE);
        }
    }

    public void showTasks() {
        try {
            // Kiem tra danh sach trong truoc khi lay du lieu
            if (taskRepository.isEmpty()) {
                throw new Exception(Message.ERROR_EMPTY);
            }
            // yeu cau View hien thi
            List<ResponseDTO> response = taskRepository.getAllTasks();
            taskView.setResponseDTO(response);
            taskView.printTask();
        } catch (Exception ex) {
            taskView.printMessage(ex.getMessage());
        }
    }

    // Getters và Setters cho DTO
    public void setDto(TaskDTO dto) {
        this.taskDto = dto;
    }
}
