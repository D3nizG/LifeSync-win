package App.repository;

import App.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, String> {
    List<UserPreference> findByUserId(String userId);
    UserPreference findByUserIdAndDescription(String userId, String description);
}
