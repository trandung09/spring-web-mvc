package com.tvd;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        List<Integer> numbers = List.of(10, 5, 3, 12, 6, 7, 5, 3);

        // 1. Duyệt numbers
        numbers.forEach(System.out::println);

        // 2. Tìm các giá trị chẵn trong list
        numbers.stream().filter(o -> o % 2 == 0).forEach(System.out::println);

        // 3. Tìm các giá trị > 5 trong list
        numbers.stream().filter(o -> o > 5).forEach(System.out::println);

        //4. Tìm giá trị max trong list
        int maxValue = numbers.stream().max(Integer::compareTo).orElse(Integer.MAX_VALUE);
        System.out.println(maxValue);

        // 5. Tìm giá trị min trong list
        int minValue = numbers.stream().min(Integer::compareTo).orElse(Integer.MIN_VALUE);
        System.out.println(minValue);

        // 6. Tính tổng các phần tử của mảng
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);

        // 7. Lấy danh sách các phần tử không trùng nhau
        Set<Integer> setNumber = new HashSet<>(numbers);
        setNumber.forEach(System.out::println);

        // 8. Lấy 5 phần tử đầu tiên trong mảng
        List<Integer> nNumbers = numbers.stream().limit(5).toList();
        nNumbers.forEach(System.out::println);

        // 9. Lấy phần tử từ thứ 3 -> thứ 5
        List<Integer> mNumbers = numbers.stream().skip(2).limit(5).toList();
        mNumbers.forEach(System.out::println);

        // 10. Lấy phần tử đầu tiên > 5
        int v10 = numbers.stream().filter(o -> o > 5).findFirst().orElse(5);
        System.out.println(v10);

        //11. Kiểm tra xem list có phải là list chẵn hay không
        boolean v11 = numbers.stream().allMatch(o -> o % 2 == 0);
        System.out.println(v11);

        // 12. Kiểm tra xem list có phần tử > 10 hay không
        boolean v12 = numbers.stream().anyMatch(o -> o > 10);
        System.out.println(v12);

        // 13. Có bao nhiêu phần tử > 5
        long v13 = numbers.stream().filter(o -> o > 5).count();
        System.out.println(v13);

        // 14. Nhân đôi các phần tủ trong list và trả về list mới
        List<Integer> v14 = numbers.stream().map(o -> o * 2).toList();
        v14.forEach(System.out::println);

        // 15. Kiểm tra xem list không chứa giá trị nào = 8 hay không
        boolean v15 = numbers.stream().allMatch(o -> o != 8);
        System.out.println(v15);
    }
}