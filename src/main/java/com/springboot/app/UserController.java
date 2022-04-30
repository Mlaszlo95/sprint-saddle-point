package com.springboot.app;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
	/*@RequestMapping(value = "/matrix", method = RequestMethod.POST)

	public @ResponseBody String addNewWorker(@RequestBody String jsonString) {
		System.out.print(jsonString);
	    //do business logic
	    return "Sikeres a beillesztes!";
	}*/
	
	static double matrix[][] = null;
	static int N = 0;
	public static double saddlePoint = 0;
	
	@RequestMapping(
		value = "/matrix", 
		method = RequestMethod.POST,
		consumes = "text/plain")
	
	public String process(@RequestBody String payload) throws Exception {
		payload.replace("", "\n\n");
		String[] lines = payload.split("\r\n|\r|\n");
		N  = lines.length;
		String[] currencies = payload.split("\\W+");
		matrix = new double[N][N];
		double[] current = new double[N*N+5];
		
		
		for(int i = 0; i < currencies.length;i++) {
			current[i] = Double.parseDouble(currencies[i]);
			
		}
		for(int i = 0; i < N;i++) {
			for(int j = 0; j < N;j++) {
				matrix[j][i] = current[j * N + i];
			}
		}
		 
		return "Elvegezve!";
	}
	
	@RequestMapping(
			value = "/matrix", 
			method = RequestMethod.GET,
			consumes = "text/plain")
	public String solutaion(@RequestBody String payload) throws Exception {
		if(matrix == null) {
			return "Elsőnek Post-ot használd hogy beilleszd a mátrix értékét!";
		}
		System.out.print(N);
		
		if(findSaddleIsExist()) {
			return "Nyereg pont a következö: " + saddlePoint;
		}else {
			return "Nincs a matrixba nyeregpont!";
		}
		
	}
	
	public static boolean findSaddleIsExist()
    {
	    for (int i = 0; i < N; i++)
	    {
	        double min_row = matrix[i][0];
	        int col_ind = 0;
	        for (int j = 1; j < N; j++)
	        {
	            if (min_row > matrix[i][j])
	            {
	                min_row = matrix[i][j];
	                col_ind = j;
	            }
	        }
	        int k;
	        for (k = 0; k < N; k++)
	 
	            if (min_row < matrix[k][col_ind])
	                break;
	 
	        // If saddle point is present in this row then
	        // print it
	        if (k == N)
	        {
	           saddlePoint = min_row;
	           return true;
	        }
	    }
	 
	    // If Saddle Point not found
	    return false;
    }
	
}
