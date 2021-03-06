import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void beforeEachTest(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @AfterEach
    public void afterEach(){
        restaurant = null;
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.doReturn(LocalTime.parse("13:30:00")).when(mockRestaurant).getCurrentTime();
        assertTrue(mockRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.doReturn(LocalTime.parse("23:59:00")).when(mockRestaurant).getCurrentTime();
        assertFalse(mockRestaurant.isRestaurantOpen());

    }
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_equal_to_open_or_closing_time(){
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.doReturn(LocalTime.parse("10:30:00")).when(mockRestaurant).getCurrentTime();
        assertTrue(mockRestaurant.isRestaurantOpen());
        Mockito.doReturn(LocalTime.parse("22:00:00")).when(mockRestaurant).getCurrentTime();
        assertTrue(mockRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    @Test
    public void get_selected_item_total_should_return_zero_if_no_item_is_selected(){
        List<String> itemsSelected = new ArrayList<String>();
        assertEquals(0,restaurant.getSelectedItemTotal(itemsSelected));
    }
    @Test
    public void get_selected_item_total_should_return_1_item_total_if_1_item_is_selected(){
        List<String> itemsSelected = new ArrayList<String>();
        itemsSelected.add("Sweet corn soup");
        assertEquals(119,restaurant.getSelectedItemTotal(itemsSelected));
    }
    @Test
    public void get_selected_item_total_should_return_item_total_of_all_items_selected(){
        List<String> itemsSelected = new ArrayList<String>();
        itemsSelected.add("Sweet corn soup");
        itemsSelected.add("Vegetable lasagne");
        assertEquals(388,restaurant.getSelectedItemTotal(itemsSelected));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}