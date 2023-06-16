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
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderControllerTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderController orderController;

    public List<Order> buildOrders(){
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setOrderId(111);
        order.setProductType("Led TV");
        order.setAddress("431 Leslie Ln 72719");
        order.setPrice(499);
        orderList.add(order);

        return orderList;
    }
    @Test
    public void saveOrderTest(){

        when(orderRepository.saveAll(anyList())).thenReturn(buildOrders());
        assertEquals("\nOrder Placed with order ID :111",orderController.saveOrder(buildOrders().get(0)));
    }

}
