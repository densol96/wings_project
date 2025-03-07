package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.Test;

public interface ITestRepo extends JpaRepository<Test, Integer> {

}
