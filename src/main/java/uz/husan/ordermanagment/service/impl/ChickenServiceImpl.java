package uz.husan.ordermanagment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.husan.ordermanagment.dto.chicken.ChickenAddDTO;
import uz.husan.ordermanagment.dto.chicken.ChickenShowDTO;
import uz.husan.ordermanagment.entity.Chicken;
import uz.husan.ordermanagment.entity.User;
import uz.husan.ordermanagment.message.ResponseMessage;
import uz.husan.ordermanagment.repository.ChickenRepository;
import uz.husan.ordermanagment.repository.UserRepository;
import uz.husan.ordermanagment.service.AuthService;
import uz.husan.ordermanagment.service.ChickenService;

import java.util.Optional;


@RequiredArgsConstructor
@Service("chickenService")
public class ChickenServiceImpl implements ChickenService {
    private final ChickenRepository chickenRepository;
    private final UserRepository userRepository;

    public ChickenShowDTO getChicken(Chicken chicken) {
        Optional<User> byId = userRepository.findById(chicken.getOwner().getId());
        if (byId.isEmpty()) {
            return null; // or throw an exception
        }

        ChickenShowDTO chickenShowDTO = new ChickenShowDTO();
        chickenShowDTO.setId(chicken.getId());
        chickenShowDTO.setChickenName(chicken.getChickenName());
        chickenShowDTO.setChickenLocation(chicken.getChickenLocation());
        chickenShowDTO.setPhoneNumber(chicken.getPhoneNumber());
        chickenShowDTO.setOwnerName(chicken.getOwner().getFullName());
        chickenShowDTO.setImageUrl(chicken.getImageUrl());

        return chickenShowDTO;
    }

    @Override
    public ResponseMessage getAllUsers(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<User> all = userRepository.findAll(pageRequest);
        if (all.isEmpty()){
            return ResponseMessage.builder().success(false).message("user no such exists").data(null).build();
        }
        return ResponseMessage
                .builder()
                .message("users fetched successfully")
                .success(true)
                .data(all)
                .build();
    }

    @Override
    public ResponseMessage getAllChickens(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ChickenShowDTO> all = chickenRepository.findAll(pageRequest).map(this::getChicken );
        if (all.isEmpty()){
            return ResponseMessage.builder().success(false).message("Chicken no such exists").data(null).build();
        }
        return ResponseMessage
                .builder()
                .message("meals fetched successfully")
                .success(true)
                .data(all)
                .build();
    }

    @Override
    public ResponseMessage createChicken(ChickenAddDTO chickenAddDTO) {
        Optional<User> byId = userRepository.findById(chickenAddDTO.getOwnerId());
        if (byId.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Owner not found")
                    .data(null)
                    .build();
        }
        Chicken chicken = new Chicken();
        chicken.setChickenName(chickenAddDTO.getChickenName());
        chicken.setChickenLocation(chickenAddDTO.getChickenLocation());
        chicken.setPhoneNumber(chickenAddDTO.getPhoneNumber());
        chicken.setImageUrl(chickenAddDTO.getImageUrl());
        chicken.setOwner(byId.get());

        Chicken savedChicken = chickenRepository.save(chicken);
        return ResponseMessage.builder()
                .success(true)
                .message("Chicken created successfully")
                .data(getChicken(savedChicken))
                .build();
    }

    @Override
    public ResponseMessage updateChicken(Long id, ChickenAddDTO chickenAddDTO) {
        Optional<User> byId = userRepository.findById(chickenAddDTO.getOwnerId());
        if (byId.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Owner not found")
                    .data(null)
                    .build();
        }
        Optional<Chicken> optionalChicken = chickenRepository.findById(id);
        if (optionalChicken.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Chicken not found")
                    .data(null)
                    .build();
        }

        Chicken chicken = optionalChicken.get();
        chicken.setChickenName(chickenAddDTO.getChickenName());
        chicken.setChickenLocation(chickenAddDTO.getChickenLocation());
        chicken.setPhoneNumber(chickenAddDTO.getPhoneNumber());
        chicken.setImageUrl(chickenAddDTO.getImageUrl());
        chicken.setOwner(byId.get());

        Chicken updatedChicken = chickenRepository.save(chicken);
        return ResponseMessage.builder()
                .success(true)
                .message("Chicken updated successfully")
                .data(getChicken(updatedChicken))
                .build();
    }

    @Override
    public ResponseMessage deleteChicken(Long id) {
        Optional<Chicken> optionalChicken = chickenRepository.findById(id);
        if (optionalChicken.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Chicken not found")
                    .data(null)
                    .build();
        }
        chickenRepository.deleteById(id);
        return ResponseMessage.builder()
                .success(true)
                .message("Chicken deleted successfully")
                .data(null)
                .build();
    }
}
