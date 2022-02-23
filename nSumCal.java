import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class nSumCal
{    
    // Output array
    public static List<List<Integer>> output = new ArrayList<>();
    // Stores variables ('a'-'z') with corresponding list index
    public static Map<Character, Integer> varList;
    // Stores variables ('a'-'z') where a:1, b:2, etc
    public static char[] varCount;
    
    // Example Usage
    public static void main(String[] args){
        int[] listTest = {-5,-5,-4,-4,-3,-3,-2,-2,-1,-1,0,0,1,1,2,2,3,3,4,4,5,5};
        List<List<Integer>> print = nSum(listTest, 0, 5);
        System.out.println("done");
        
        listToString(print);
    }

    // Main nSum program
    public static List<List<Integer>> nSum(int[] list, int target, int n) throws IllegalArgumentException
    {
        // Emptying variables
        output = new ArrayList<>();
        varCount = new char[n - 2];
        varList = new HashMap<>();
        
        // Argument checking
        if (n < 0)
            throw new IllegalArgumentException("n must not be negative");
        if (n > list.length)
            throw new IllegalArgumentException("n must be less than length of list");
        
        // Scenario Determining for n 
        if (n > 2) 
        {
            // Setting Variables in Place
            String var = "abcdefghijklmnopqrstuvwxyz";
            Arrays.sort(list);
            list = noDuplicates(list);
            for (int i = 0; i < n - 2; i++)
            {
                varCount[i] = var.charAt(i);
                varList.put(var.charAt(i), i);
            }
            
            nRecursion(list, target, 0, list.length - n + 1);
        }
        
        else if (n == 2)
        {
            twoSum(list, target);
        }
        
        
        else if (n == 1)
        {
            output.add(Arrays.asList(target));
        }
        
        return output;
    }
    
    public static int[] noDuplicates(int[] sortedList)
    {
        int k = 1;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < sortedList.length - 1; i++)
        {
            if (sortedList[i] != sortedList[i+1])
            {
                list.add(sortedList[i]);
                k++;
            }
        }
        list.add(sortedList[sortedList.length - 1]);
        
        int[] noDupList = new int[k];
        for (int i = 0; i < k; i++)
        {
            noDupList[i] = list.get(i);
        }
        
        return noDupList;
    }
    
    public static void twoSum(int[] list, int target)
    {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < list.length; i++)
        {
            int num = target - list[i];
            if (map.containsKey(num))
            {
                output.add(new ArrayList<Integer>(Arrays.asList(num, list[i])));
            }
            map.put(list[i], i);
        }
    }
    
    public static void nRecursion(int[] list, int target, int i, int maxIndex)
    {
        // Recursion stops when last variable is done
        if (i < varCount.length)
        {   
            // Until variable's current index reaches its max
            while(varList.get(varCount[i]) < maxIndex)
            {
             nRecursion(list, target, i+1, maxIndex+1);

             // Resets variables infront of current variable 
             for (int p = i + 1; p < varCount.length; p++)
             {
                 varList.put(varCount[p], varList.get(varCount[p-1]) + 2);   
             }
             
             // L-R pointer algorithmn starts
             int left = varList.get(varCount[varCount.length - 1]) + 1;
             int right = list.length - 1;
             while (left < right)
             {
                 List<Integer> currNumbers = new ArrayList<>();
                 int currSum = 0;
                 
                 // Adds sum of variables
                 for (int j = 0; j < varCount.length; j++)
                 {
                     currSum += list[varList.get(varCount[j])];
                 }
                 currSum += (list[left] + list[right]);
                 
                 // Add list to output if sum matches target 
                 if (currSum == target)
                 {
                     for (int j = 0; j < varCount.length; j++)
                     {
                         currNumbers.add(list[varList.get(varCount[j])]);
                     }
                     currNumbers.add(list[left]);
                     currNumbers.add(list[right]);
                     output.add(currNumbers);
                     
                     // Updates L-R
                     left++;
                     right--;
                 }
                 else if (currSum > target)
                    right--;
                 else
                    left++;
                     
             }
             // Update variable by 1 increment
             varList.put(varCount[i], varList.get(varCount[i]) + 1);
            }
        }
    }
    
    public static void listToString(List<List<Integer>> list)
    {
        for (List<Integer> l1 : list) {
            System.out.print("[");
            for (int i = 0; i < l1.size() - 1; i++) {
                System.out.print(l1.get(i) + ", ");
            }
            System.out.println(l1.get(l1.size() - 1) + "]");
        } 
    }
    
}