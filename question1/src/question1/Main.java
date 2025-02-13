package question1;

public class Main {
	 public static int findNearestValue(int[] arr, int target) {
	        // กรณี array ว่างหรือมีค่าเดียว
	        if (arr == null || arr.length == 0) return -1;
	        if (arr.length == 1) return arr[0];

	        // ถ้าค่าเป้าหมายน้อยกว่าค่าแรกหรือมากกว่าค่าสุดท้าย
	        if (target <= arr[0]) return arr[0];
	        if (target >= arr[arr.length - 1]) return arr[arr.length - 1];

	        // ใช้ Binary Search
	        int left = 0;
	        int right = arr.length - 1;

	        while (left <= right) {
	            int mid = left + (right - left) / 2;

	            // เจอค่าที่ต้องการพอดี
	            if (arr[mid] == target) {
	                return arr[mid];
	            }

	            // ถ้าค่ากลางน้อยกว่าเป้าหมาย
	            if (arr[mid] < target) {
	                // ถ้าค่าถัดไปมากกว่าเป้าหมาย ให้เปรียบเทียบว่าค่าไหนใกล้กว่า
	                if (mid + 1 < arr.length && arr[mid + 1] > target) {
	                    return getClosest(arr[mid], arr[mid + 1], target);
	                }
	                left = mid + 1;
	            }
	            // ถ้าค่ากลางมากกว่าเป้าหมาย
	            else {
	                // ถ้าค่าก่อนหน้าน้อยกว่าเป้าหมาย ให้เปรียบเทียบว่าค่าไหนใกล้กว่า
	                if (mid - 1 >= 0 && arr[mid - 1] < target) {
	                    return getClosest(arr[mid - 1], arr[mid], target);
	                }
	                right = mid - 1;
	            }
	        }
	        return arr[left];
	    }
	 
	   // ฟังก์ชันช่วยหาค่าที่ใกล้เคียงที่สุด
    private static int getClosest(int val1, int val2, int target) {
        if (target - val1 >= val2 - target) {
            return val2;
        }
        return val1;
    }
	   // ตัวอย่างการใช้งาน
    public static void main(String[] args) {
        // สร้าง array ตัวอย่างขนาดใหญ่
        int[] arr = new int[1000001];
        for (int i = 0; i <= 1000000; i++) {
            arr[i] = i;
        }

        int target = 900000;
        int result = findNearestValue(arr, target);
        
        System.out.println("ค่าที่ใกล้เคียง " + target + " ที่สุดคือ: " + result);
        System.out.println("ความต่าง: " + Math.abs(target - result));
    }

}
