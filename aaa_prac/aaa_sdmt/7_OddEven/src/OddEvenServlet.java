import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OddEvenServlet
 */
@WebServlet("/OddEvenServlet")
public class OddEvenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OddEvenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        int i, j;
        String sInput = new String(request.getParameter("input"));
        String numbers[] = sInput.split(" ");
        int count = 0;
        //int l = 0;
        for (i = 0; i < numbers.length; i++) {
        	count++;
        }
         System.out.println(count);
        int[] arr = new int[count];

        for (i = 0, j = 0; i < numbers.length && j < count; i++, j++) {
        	arr[j] = Integer.parseInt(String.valueOf(numbers[i]));
        }
        for(int l = 0; i < arr.length; l++)
    		System.out.print(arr[l]+" ");
        PrintWriter pw = response.getWriter();
        String sorted = OddEvenSort.sort(arr);
        pw.print("sorted "+sorted + " ");
        pw.print("");

    }

}
