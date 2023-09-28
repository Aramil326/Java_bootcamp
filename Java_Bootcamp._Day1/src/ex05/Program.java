import java.util.Scanner;


public class Program {
    private static String[] names;
    private static int[][] classes;
    private static int[][] attendance;
    private static int[][] result;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        getNames(scanner);
        getClasses(scanner);
        sortClasses();
        setCalendar();
        getAttendance(scanner);
        sortAttendance();
        output();
    }

    private static void getNames(Scanner scanner) {
        String[] temp = new String[11];
        int namesLength = 0;
        for (int i = 0; i < 11; i++) {
            temp[i] = scanner.next();
            if (temp[i].equals(".")) {
                break;
            } else {
                namesLength++;
            }
        }
        namesLength = namesLength > 9 ? 10 : namesLength;
        names = new String[namesLength];
        for (int i = 0; i < namesLength; i++) {
            names[i] = temp[i];
        }
    }

    private static void getClasses(Scanner scanner) {
        String[][] temp = new String[11][2];
        int classesLength = 0;
        for (int i = 0; i < 11; i++) {
            temp[i][0] = scanner.next();
            if (temp[i][0].equals(".")) {
                break;
            }
            temp[i][1] = scanner.next();
            classesLength++;
        }
        classesLength = classesLength > 9 ? 10 : classesLength;
        classes = new int[classesLength][2];
        String[] weekDaysNames = new String[]{"MO", "TU", "WE", "TH", "FR", "SA", "SU"};
        for (int i = 0; i < classesLength; i++) {
            classes[i][0] = Integer.parseInt(temp[i][0]);
            for (int j = 0; j < weekDaysNames.length; j++) {
                if (temp[i][1].equals(weekDaysNames[j])) {
                    classes[i][1] = j + 1;
                    break;
                }
            }
        }
    }

    private static void sortClasses() {
        for (int i = 0; i < classes.length; i++) {
            for (int j = i; j < classes.length; j++) {
                if (classes[i][0] > classes[j][0] || classes[i][1] > classes[j][1]) {
                    int[] temp = classes[i];
                    classes[i] = classes[j];
                    classes[j] = temp;
                }
            }
        }
    }

    private static void getAttendance(Scanner scanner) {
        int[][] temp = new int[names.length * result.length][4];
        int attendanceLength = 0;
        for (int i = 0; i < names.length * result.length; i++) {
            String name = scanner.next();
            if (name.equals(".")) {
                break;
            }
            temp[i][0] = getNameId(name);

            temp[i][1] = scanner.nextInt();
            temp[i][2] = scanner.nextInt();
            String presence = scanner.next();
            if (presence.equals("NOT_HERE")) {
                temp[i][3] = -1;
            } else if (presence.equals("HERE")) {
                temp[i][3] = 1;
            }
            attendanceLength++;
        }

        attendance = new int[attendanceLength][4];
        for (int i = 0; i < attendanceLength; i++) {
            attendance[i][0] = temp[i][0];
            attendance[i][1] = temp[i][1];
            attendance[i][2] = temp[i][2];
            attendance[i][3] = temp[i][3];
        }
    }

    private static void sortAttendance() {
        for (int i = 0; i < attendance.length; i++) {
            for (int j = i; j < attendance.length; j++) {
                if (attendance[i][2] > attendance[j][2] || attendance[i][1] > attendance[j][1]) {
                    int[] temp = attendance[i];
                    attendance[i] = attendance[j];
                    attendance[j] = temp;
                }
            }
        }
    }

    private static int getNameId(String inputName) {
        int id = 0;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(inputName)) {
                id = i;
                break;
            }
        }
        return id;
    }

    private static void setCalendar() {
        int[][] temp = new int[60][3];
        int tempLength = 0;
        int[][] calendar = new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}, {7, 1}, {8, 2}, {9, 3}, {10, 4}, {11, 5}, {12, 6}, {13, 7}, {14, 1}, {15, 2}, {16, 3}, {17, 4}, {18, 5}, {19, 6}, {20, 7}, {21, 1}, {22, 2}, {23, 3}, {24, 4}, {25, 5}, {26, 6}, {27, 7}, {28, 1}, {29, 2}, {30, 3}};
        for (int[] value : calendar) {
            for (int[] aClass : classes) {
                if (value[1] == aClass[1]) {
                    temp[tempLength][0] = value[0];
                    temp[tempLength][1] = value[1];
                    temp[tempLength][2] = aClass[0];
                    tempLength++;
                }
            }
        }

        result = new int[tempLength][4];
        int resultI = 0;
        for (int[] ints : temp) {
            for (int j = 0; j < 3; j++) {
                if (ints[0] != 0) {
                    result[resultI][j] = ints[j];
                }
            }
            if ((resultI != tempLength) && (result[resultI][0] != 0)) {
                resultI++;
            }
        }
    }

    private static void output() {
        System.out.print(getSpaceStr(getEmptyStrMaxNameLen()));
        for (int i = 0; i < result.length; i++) {
            if (i != 0) {
                System.out.print("|");
            }

            System.out.print(result[i][2] + ":00 " + getWeekDay(result[i][1]));

            if (result[i][0] > 9) {
                System.out.print(" " + result[i][0]);
            } else {
                System.out.print("  " + result[i][0]);
            }

            if (i + 1 == result.length) {
                System.out.println("|");
            }
        }

        for (int i = 0; i < names.length; i++) {
            System.out.print(names[i] + getSpaceStr(getEmptyStrMaxNameLen() - names[i].length()));
            for (int[] ints : result) {
                boolean isOut = false;
                for (int[] value : attendance) {
                    if (value[2] == ints[0] && value[1] == ints[2] && value[0] == i) {
                        if (value[3] == -1) {
                            System.out.print("        -1|");
                        } else if (value[3] == 1) {
                            System.out.print("         1|");
                        }
                        isOut = true;
                    }
                }
                if (!isOut) {
                    System.out.print("          |");
                }
            }
            System.out.print("\n");
        }
    }

    private static int getEmptyStrMaxNameLen() {
        int max = 0;
        for (String name : names) {
            if (name.length() > max) {
                max = name.length();
            }
        }
        return max;
    }

    private static String getSpaceStr(int num) {
        String res = "";
        for (int i = 0; i < num; i++) {
            res += " ";
        }
        return res;
    }

    private static String getWeekDay(int num) {
        String[] weekDaysNames = new String[]{"MO", "TU", "WE", "TH", "FR", "SA", "SU"};
        return weekDaysNames[num - 1];
    }
}
