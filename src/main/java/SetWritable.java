import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

public class SetWritable implements Writable {
    HashSet<IntWritable> lineSet;

    public SetWritable() {
        lineSet = new HashSet<>();
    }

    public SetWritable(HashSet<IntWritable> lineSet) {
        this.lineSet = lineSet;
    }

    public HashSet<IntWritable> getLineSet() {
        return lineSet;
    }

    public void setLineSet(HashSet<IntWritable> lineSet) {
        this.lineSet = lineSet;
    }

    public void insert(IntWritable integer) {
        lineSet.add(integer);
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder("{ ");
        Iterator<IntWritable> it = lineSet.iterator();
        if (it.hasNext())
            ans.append(it.next().get());
        while (it.hasNext()) {
            ans.append(", ").append(it.next().get());
        }
        ans.append(" }");
        return ans.toString();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(lineSet.size());
        for (IntWritable var: lineSet) {
            var.write(dataOutput);
        }
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        int size = dataInput.readInt();
        for (int i = 0; i < size; i++) {
            IntWritable tmp = new IntWritable();
            tmp.readFields(dataInput);
            lineSet.add(tmp);
        }
    }
}
