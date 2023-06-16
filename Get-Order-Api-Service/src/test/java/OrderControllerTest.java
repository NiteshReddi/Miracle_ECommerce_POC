import org.example.controller.OrderController;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderControllerTest {


    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderController orderController;

//    OrderController orderController = Mockito.mock(OrderController.class);
//
//    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);


    public List<Order> buildOrders(){
    List<Order> orderList = new ArrayList<>();
    Order order = new Order();
    order.setOrderId(111);
    order.setProductType("Led TV");
    order.setAddress("431 Leslie Ln 72719");
    order.setPrice(499);

    orderList.add(order);

    Order order1 = new Order();
    order1.setOrderId(222);
    order1.setProductType("LapTop");
    order1.setAddress("431 Leslie Ln 72719");
    order1.setPrice(1199);

    orderList.add(order1);

    return orderList;
    }

    @Test
    public void getAllOrdersTest(){
//        when(orderRepository.findAll()).thenReturn(buildOrders());
//    //    System.out.println(orderRepository.findAll());
//    //    System.out.println("*****"+orderController.getAllOrders());
//        assertEquals(2,orderController.getAllOrders().size());
    }

    @Test
    public void getOrderByIdTest(){
//        when(orderRepository.findByOrderId(anyInt())).thenReturn(buildOrders().get(0));
//        ResponseEntity<?> result = orderController.getOrderById(111);
//        assertEquals(buildOrders().get(0), result.getBody());
    }
}
