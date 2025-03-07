package lv.wings.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// @Entity
// @Getter
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// class TestParent {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Setter(value = AccessLevel.NONE)
//     private Integer id;
// }

@Entity
@Getter
public class Test extends TestParent {
    private String name;
}

// @Entity
// @Getter
// class TestAdditional extends TestParent {
// // @Id
// // @GeneratedValue(strategy = GenerationType.IDENTITY)
// // @Setter(value = AccessLevel.NONE)
// // private Integer id;

// private String surname;
// }
