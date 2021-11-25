import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InverseIndex {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

        private IntWritable lineNumber;

        private String matchPattern(String mode, String regex, String errMsg) {
            Pattern quotePattern = Pattern.compile(regex);
            Matcher quoteMatch = quotePattern.matcher(mode);
            if (quoteMatch.find()) {
                return quoteMatch.group(0);
            } else {
                throw new RuntimeException(errMsg);
            }
        }

        private int getLineNumber(String line) {
            // Parse line number
            String rawLine = matchPattern(line,"T\\d+", "Unable to parse line sequence number");
            return Integer.parseInt(rawLine.substring(1));
        }

        private String getSentence(String line) {
            // Parse sentence
            String rawLine = matchPattern(line, "\"[^\"]*\"", "Unable to find the sentence at the line " + lineNumber.get());
            return rawLine.substring(1, rawLine.length() - 1);
        }

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (line.trim().isEmpty())
                return;
            lineNumber = new IntWritable(getLineNumber(line));
            String sentence = getSentence(line);

            // Split & Insert
            StringTokenizer tokenizer = new StringTokenizer(sentence);
            HashSet<Text> words = new HashSet<>();
            while (tokenizer.hasMoreTokens()) {
                Text text = new Text(tokenizer.nextToken().toLowerCase());
                words.add(text);
            }

            // Update the context
            for (Text word : words) {
                context.write(word, lineNumber);
            }
        }
    }

    public static class InverseIndexReducer extends Reducer<Text, IntWritable, Text, SetWritable> {

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            SetWritable res = new SetWritable();
            for (IntWritable iter: values) {
                res.insert(iter.get());
            }
            context.write(key, res);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Inverse Index");
        job.setJarByClass(InverseIndex.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setReducerClass(InverseIndexReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(SetWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
