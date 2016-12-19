

class ShellSort {

    private int minRange;
    private int maxRange;
    private long complexity;

    public ShellSort(int minRange, int maxRange) {
        this.minRange = minRange
        this.maxRange = maxRange
        this.complexity = 0
    }

    public def generateRandomNumbers() {
        Random rand = new Random()
        int max = 10000
        def randomIntegerList = []
        (this.minRange..this.maxRange).each {
            randomIntegerList << rand.nextInt(max+1)
        }
        return randomIntegerList
    }

    public void shellSortedNumbers(def originNumbers) {
        int d = originNumbers.size() / 2 //�������������� ���.
        while (d > 0) //���� ��� �� 0
        {
            for (int i = 0; i < (originNumbers.size() - d); i++)
            {
                def j = i
                //����� ���� ������� � i-�� ��������
                while (j >= 0 && originNumbers[j] > originNumbers[j + d])
                //���� �� ������ � ������ �������
                //� ���� ��������������� ������� ������
                //��� ������� ����������� �� ���������� ����
                {
                    //������ �� �������
                    def temp = originNumbers[j]
                    originNumbers[j] = originNumbers[j + d]
                    originNumbers[j + d] = temp
                    j--
                    complexity++
                }
            }
            d = d / 2   //��������� ���
        }
    }

    public int getComplexity() {
        return this.complexity
    }

    public static void main(String[] args) {
        ShellSort shellSort = new ShellSort(1, 100)
        def numbers = shellSort.generateRandomNumbers()
        shellSort.shellSortedNumbers(numbers)
        println shellSort.getComplexity()
    }
}
