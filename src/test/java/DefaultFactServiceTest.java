import com.example.demo.entity.Fact;
import com.example.demo.entity.Numbeer;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FactCategoryRepository;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.defaultt.DefaultFactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

public class DefaultFactServiceTest {

  @Mock private FactRepository factRepository;

  @Mock private NumberRepository numberRepository;

  @InjectMocks private DefaultFactService defaultFactService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindFact() {
    // Arrange
    String description = "Fact description";
    Fact fact = new Fact();
    fact.setDescription(description);

    when(factRepository.findByDescription(description)).thenReturn(fact);

    // Act
    Fact result = defaultFactService.findFact(description);

    // Assert
    assertEquals(description, result.getDescription());
  }

  @Test
  void testGetFactByNumberId() {
    // Arrange
    long number = 1L;
    Numbeer numbeer = new Numbeer();
    numbeer.setNumberData(number);
    Fact fact = new Fact();
    fact.setNumber(numbeer);

    when(numberRepository.findByNumberData(number)).thenReturn(numbeer);
    when(factRepository.findByNumber(numbeer)).thenReturn(fact);

    // Act
    Fact result = defaultFactService.getFactByNumberId(number);

    // Assert
    assertEquals(number, result.getNumber().getNumberData());
  }

  @Test
  void testCreateFact() {
    // Arrange
    long number = 1L;
    String description = "Fact description";
    Numbeer numbeer = new Numbeer();
    numbeer.setNumberData(number);
    Fact fact = new Fact();
    fact.setNumber(numbeer);
    fact.setDescription(description);

    when(numberRepository.findByNumberData(number)).thenReturn(null);
    when(numberRepository.save(any(Numbeer.class))).thenReturn(numbeer);
    when(factRepository.save(any(Fact.class))).thenReturn(fact);

    // Act
    Fact result = defaultFactService.createFact(number, description);

    // Assert
    verify(numberRepository, times(1)).save(any(Numbeer.class));
    verify(factRepository, times(1)).save(any(Fact.class));
    assertEquals(number, result.getNumber().getNumberData());
    assertEquals(description, result.getDescription());
  }
}
