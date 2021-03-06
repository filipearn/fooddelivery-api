package arn.filipe.fooddelivery.domain.service;

import arn.filipe.fooddelivery.domain.exception.BusinessException;
import arn.filipe.fooddelivery.domain.exception.UserNotFoundException;
import arn.filipe.fooddelivery.domain.model.Team;
import arn.filipe.fooddelivery.domain.model.User;
import arn.filipe.fooddelivery.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public static final String USER_IN_USE = "User with id %d can't be removed. Resource in use.";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return verifyIfExistsOrThrow(id);
    }

    @Transactional
    public User save(User user){
        userRepository.detach(user);

        Optional<User> existentUser = userRepository.findByEmail(user.getEmail());

        if(existentUser.isPresent()){
            throw new BusinessException(
                    String.format("Already exist a user registered with email '%s'", user.getEmail()));
        }

        if (user.isNew()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(Long id, String actualPassword, String newPassword){
        User user = verifyIfExistsOrThrow(id);

        if (!passwordEncoder.matches(actualPassword, user.getPassword())) {
            throw new BusinessException("Actual password doesn't match the old password.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void associateTeam(Long userId, Long teamId){
        User user = verifyIfExistsOrThrow(userId);
        Team team = teamService.verifyIfExistsOrThrow(teamId);

        user.associateTeam(team);
    }

    @Transactional
    public void disassociateTeam(Long userId, Long teamId){
        User user = verifyIfExistsOrThrow(userId);
        Team team = teamService.verifyIfExistsOrThrow(teamId);

        user.disassociateTeam(team);
    }

//    @Transactional
//    public void delete(Long id){
//        try{
//            userRepository.deleteById(id);
//            userRepository.flush();
//        } catch (EmptyResultDataAccessException e) {
//            throw new UserNotFoundException(id);
//        }catch (DataIntegrityViolationException e) {
//            throw new EntityInUseException(
//                    String.format(USER_IN_USE, id));
//        }
//
//    }

    public User verifyIfExistsOrThrow(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
