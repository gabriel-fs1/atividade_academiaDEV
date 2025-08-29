import controller.MenuController;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import repository.SupportTicketRepository;
import repository.UserRepository;
import repository.impl.CourseImpl;
import repository.impl.EnrollmentImpl;
import repository.impl.SupportTicketImpl;
import repository.impl.UserImpl;
import service.CourseService;
import service.EnrollmentService;
import service.ReportService;
import service.SupportTicketService;
import service.UserService;
import view.MenuView;

public class Main {
    public static void main(String[] args) {
  
        UserRepository userRepo = new UserImpl();
        CourseRepository courseRepo = new CourseImpl();
        EnrollmentRepository enrollmentRepo = new EnrollmentImpl();
        SupportTicketRepository ticketRepo = new SupportTicketImpl();

        
        CourseService courseService = new CourseService(courseRepo);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepo, courseRepo, userRepo);
        SupportTicketService supportTicketService = new SupportTicketService(ticketRepo);
        ReportService reportService = new ReportService(courseRepo, enrollmentRepo, userRepo);
        UserService userService = new UserService(userRepo, enrollmentService);
        InitialData.load(userRepo, courseRepo, ticketRepo, enrollmentRepo);

        MenuView view = new MenuView();
        MenuController controller = new MenuController(userService, courseService, enrollmentService, supportTicketService, view, reportService);
        
        controller.start();
    }
}