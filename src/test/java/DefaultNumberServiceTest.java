import com.example.demo.entity.Numbeer;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.defaultt.DefaultNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** The type Default number service test. */
public class DefaultNumberServiceTest {

  @InjectMocks private DefaultNumberService defaultNumberService;

  @Mock private NumberRepository numberRepository;

  /** Init. */
  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  /** Test add number. */
  @Test
  public void testAddNumber() {
    // Arrange
    long numberData = 1L;
    Numbeer numbeer = new Numbeer();
    numbeer.setNumberData(numberData);
    when(numberRepository.findByNumberData(numberData)).thenReturn(null);
    when(numberRepository.save(any(Numbeer.class))).thenReturn(numbeer);

    // Act
    ResponseEntity<String> response = defaultNumberService.addNumber(numberData);

    // Assert
    assertEquals("Number added successfully: " + numberData, response.getBody());
  }

  /** Test del number. */
  @Test
  void testDelNumber() {
    // Arrange
    String number = "1";
    long numberData = Long.parseLong(number);
    Numbeer numbeer = new Numbeer();
    numbeer.setNumberData(numberData);
    when(numberRepository.findByNumberData(numberData)).thenReturn(numbeer);
    doNothing().when(numberRepository).delete(numbeer);

    // Act
    ResponseEntity<String> response = defaultNumberService.delNumber(number);

    // Assert
    verify(numberRepository, times(1)).delete(numbeer);
    assertEquals("Number delete successfully.", response.getBody());
  }

  /** Test find id by number. */
  @Test
  void testFindIdByNumber() {
    // Arrange
    long id = 1L;
    Numbeer numbeer = new Numbeer();
    numbeer.setId(id);
    when(numberRepository.findByNumberData(id)).thenReturn(numbeer);

    // Act
    long result = defaultNumberService.findIdByNumber(id);

    // Assert
    assertEquals(id, result);
  }

  /** Test create number. */
  @Test
  void testCreateNumber() {
    // Arrange
    long numberData = 1L;
    Numbeer numbeer = new Numbeer();
    numbeer.setNumberData(numberData);
    when(numberRepository.findByNumberData(numberData)).thenReturn(null);
    when(numberRepository.save(any(Numbeer.class))).thenReturn(numbeer);

    // Act
    Numbeer result = defaultNumberService.createNumber(numberData);

    // Assert
    assertEquals(numbeer, result);
  }

  /** Test find number. */
  @Test
  void testFindNumber() {
    // Arrange
    long numberData = 1L;
    Numbeer numbeer = new Numbeer();
    numbeer.setNumberData(numberData);
    when(numberRepository.findByNumberData(numberData)).thenReturn(numbeer);

    // Act
    Numbeer result = defaultNumberService.findNumber(numberData);

    // Assert
    assertEquals(numbeer, result);
  }

  /** Test update number. */
  @Test
  void testUpdateNumber() {
    // Arrange
    long id = 1L;
    long numberData = 2L;
    Numbeer numbeer = new Numbeer();
    numbeer.setId(id);
    numbeer.setNumberData(numberData);
    when(numberRepository.findById(id)).thenReturn(Optional.of(numbeer));
    when(numberRepository.save(any(Numbeer.class))).thenReturn(numbeer);

    // Act
    defaultNumberService.updateNumber(id, numberData);

    // Assert
    verify(numberRepository, times(1)).save(numbeer);
  }

  /** Test delete number. */
  @Test
  void testDeleteNumber() {
    // Arrange
    long id = 1L;
    doNothing().when(numberRepository).deleteById(id);

    // Act
    defaultNumberService.deleteNumber(id);

    // Assert
    verify(numberRepository, times(1)).deleteById(id);
  }

  /** Test add number with invalid number format. */
  @Test
  void testAddNumberWithInvalidNumberFormat() {
    // Arrange
    String number = "not a number"; // This will cause a NumberFormatException

    // Act and Assert
    assertThrows(NumberFormatException.class, () -> Long.parseLong(number));
  }

  /** Test del number with invalid number format. */
  @Test
  void testDelNumberWithInvalidNumberFormat() {
    // Arrange
    String number = "not a number"; // This will cause a NumberFormatException

    // Act
    ResponseEntity<String> response = defaultNumberService.delNumber(number);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Invalid number format", response.getBody());
  }

  /** Test all enum values present. */
  @Test
  void testAllEnumValuesPresent() {
    // Arrange
    DefaultNumberService.Type[] expected = {
      DefaultNumberService.Type.TRIVIA,
      DefaultNumberService.Type.MATH,
      DefaultNumberService.Type.YEAR
    };

    // Act
    DefaultNumberService.Type[] actual = DefaultNumberService.Type.values();

    // Assert
    assertArrayEquals(expected, actual, "All expected enum values are present");
  }
}
