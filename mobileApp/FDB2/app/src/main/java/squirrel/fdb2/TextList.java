package squirrel.fdb2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TextList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] first;
    private final String[] second;
    private final String[] third;

    public TextList(Activity context,
                      String[] first, String[] second, String[] third) {
        super(context, R.layout.multi_text_list, first);
        this.context = context;
        this.first = first;
        this.second = second;
        this.third = third;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.multi_text_list, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(first[position]);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.txt2);
        txtTitle2.setText(second[position]);
        TextView txtTitle3 = (TextView) rowView.findViewById(R.id.txt3);
        txtTitle3.setText(third[position]);
        return rowView;
    }
}