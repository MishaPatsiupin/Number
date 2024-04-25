import com.example.demo.entity.Category;
import com.example.demo.entity.Fact;
import com.example.demo.entity.FactCategory;
import com.example.demo.entity.Numbeer;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FactCategoryRepository;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.defaultt.DefaultFactCategoryService;
import com.example.demo.utils.SimpleCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/** The type Default fact category service test. */
public class DefaultFactCategoryServiceTest {

  @Mock private FactCategoryRepository factCategoryRepository;

  @Mock private CategoryRepository categoryRepository;

  @Mock private FactRepository factRepository;

  @Mock private NumberRepository numberRepository;

  @Mock private SimpleCache simpleCache;

  @InjectMocks private DefaultFactCategoryService defaultFactCategoryService;

  /** Sets up. */
@BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /** Test create fact category. */
@Test
  void testCreateFactCategory() {
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category()));
    when(factRepository.findById(anyLong())).thenReturn(Optional.of(new Fact()));
    when(factCategoryRepository.save(any(FactCategory.class))).thenAnswer(i -> i.getArguments()[0]);

    FactCategory result = defaultFactCategoryService.createFactCategory(1L, 1L);

    assertNotNull(result);
    assertNotNull(result.getCategory());
    assertNotNull(result.getFact());
  }

  /** Test delete fact category. */
@Test
  void testDeleteFactCategory() {
    assertDoesNotThrow(() -> defaultFactCategoryService.deleteFactCategory(1L));
  }

  /** Test update fact category. */
@Test
  void testUpdateFactCategory() {
    when(factCategoryRepository.findById(anyLong())).thenReturn(Optional.of(new FactCategory()));
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category()));
    when(factRepository.findById(anyLong())).thenReturn(Optional.of(new Fact()));
    when(factCategoryRepository.save(any(FactCategory.class))).thenAnswer(i -> i.getArguments()[0]);

    assertDoesNotThrow(() -> defaultFactCategoryService.updateFactCategory(1L, 1L, 1L, "author"));
  }

  /** Test get facts by fact and category. */
@Test
  void testGetFactsByFactAndCategory() {
    when(numberRepository.findByNumberData(anyLong())).thenReturn(new Numbeer());
    when(simpleCache.getFromCache(anyString()))
        .thenReturn(Collections.singletonList(new FactCategory()));

    assertFalse(defaultFactCategoryService.getFactsByFactAndCategory("1", "type").isEmpty());
  }

  /** Test get fact by fact and category. */
@Test
  void testGetFactByFactAndCategory() {
    when(numberRepository.findByNumberData(anyLong())).thenReturn(new Numbeer());
    when(defaultFactCategoryService.getFactsByFactAndCategory("1", "type"))
        .thenReturn(Collections.emptyList());

    assertNull(defaultFactCategoryService.getFactByFactAndCategory("1", "type"));
  }

  /** Test get category entity by id. */
@Test
  void testGetCategoryEntityById() {
    long id = 1L;
    Category category = new Category();
    when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

    Category result = defaultFactCategoryService.getCategoryEntityById(id);

    assertEquals(category, result);
  }

  /** Test update cache. */
@Test
  void testUpdateCache() {
    String key = "key";
    List<FactCategory> newValue = new ArrayList<>();
    defaultFactCategoryService.updateCache(key, newValue);

    verify(simpleCache, times(1)).updateCache(key, newValue);
  }

  /** Test delete cache. */
@Test
  void testDeleteCache() {
    String key = "key";
    when(simpleCache.getFromCache(key)).thenReturn(Collections.singletonList(new FactCategory()));

    defaultFactCategoryService.deleteCache(key);

    verify(simpleCache, times(1)).deleteCache(key);
  }

  /** Test get fact entity by id. */
@Test
  void testGetFactEntityById() {
    long id = 1L;
    Fact fact = new Fact();
    when(factRepository.findById(id)).thenReturn(Optional.of(fact));

    Fact result = defaultFactCategoryService.getFactEntityById(id);

    assertEquals(fact, result);
  }

  /** Test get fact category entity by id. */
@Test
  void testGetFactCategoryEntityById() {
    long id = 1L;
    FactCategory factCategory = new FactCategory();
    when(factCategoryRepository.findById(id)).thenReturn(Optional.of(factCategory));

    FactCategory result = defaultFactCategoryService.getFactCategoryEntityById(id);

    assertEquals(factCategory, result);
  }

  /** Test create fact category null category. */
@Test
  void testCreateFactCategory_NullCategory() {
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(factRepository.findById(anyLong())).thenReturn(Optional.of(new Fact()));
    when(factCategoryRepository.save(any(FactCategory.class))).thenAnswer(i -> i.getArguments()[0]);

    FactCategory result = defaultFactCategoryService.createFactCategory(1L, 1L);

    assertNull(result.getCategory());
    assertNotNull(result.getFact());
  }

  /** Test create fact category null fact. */
@Test
  void testCreateFactCategory_NullFact() {
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category()));
    when(factRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(factCategoryRepository.save(any(FactCategory.class))).thenAnswer(i -> i.getArguments()[0]);

    FactCategory result = defaultFactCategoryService.createFactCategory(1L, 1L);

    assertNotNull(result.getCategory());
    assertNull(result.getFact());
  }

  /** Test create fact category invalid id. */
@Test
  void testCreateFactCategory_InvalidId() {
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(factRepository.findById(anyLong())).thenReturn(Optional.empty());

    FactCategory result = defaultFactCategoryService.createFactCategory(-1L, -1L);

    assertNull(result);
  }

  /** Test update fact category invalid id. */
@Test
  void testUpdateFactCategory_InvalidId() {
    when(factCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> defaultFactCategoryService.updateFactCategory(-1L, 1L, 1L, "author"));
  }

  /** Test delete fact category invalid id. */
@Test
  void testDeleteFactCategory_InvalidId() {
    doThrow(new EmptyResultDataAccessException(1))
        .when(factCategoryRepository)
        .deleteById(anyLong());

    assertThrows(
        EmptyResultDataAccessException.class,
        () -> defaultFactCategoryService.deleteFactCategory(-1L));
  }

  /** Test get category entity by id not found. */
@Test
  void testGetCategoryEntityById_NotFound() {
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

    Category result = defaultFactCategoryService.getCategoryEntityById(-1L);

    assertNull(result);
  }

  /** Test get fact entity by id not found. */
@Test
  void testGetFactEntityById_NotFound() {
    when(factRepository.findById(anyLong())).thenReturn(Optional.empty());

    Fact result = defaultFactCategoryService.getFactEntityById(-1L);

    assertNull(result);
  }

  /** Test get fact category entity by id not found. */
@Test
  void testGetFactCategoryEntityById_NotFound() {
    when(factCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

    FactCategory result = defaultFactCategoryService.getFactCategoryEntityById(-1L);

    assertNull(result);
  }
}
