package com.xin.momo.fragmentTab;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xin.momo.Adapter.ConversationAdapter;
import com.xin.momo.Adapter.ConversationData;
import com.xin.momo.Adapter.ConversationDataList;
import com.xin.momo.R;
import com.xin.momo.utils.L;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConversationFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ConversationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConversationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConversationFragment newInstance(String param1, String param2) {
        ConversationFragment fragment = new ConversationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ConversationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView = (ListView) getActivity().findViewById(R.id.conversation_list_view);
        ConversationDataList conversationDataList = new ConversationDataList();
        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:20", "愤怒的小明", null));
        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:24", "愤怒的小明", null));
        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:23", "愤怒的小明", null));

        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:20", "愤怒的小明", null));
        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:24", "愤怒的小明", null));
        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:23", "愤怒的小明", null));
        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:20", "愤怒的小明", null));
        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:20", "愤怒的小明", null));
        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:24", "愤怒的小明", null));
        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:23", "愤怒的小明", null));
        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:24", "愤怒的小明", null));
        conversationDataList.addConversationData(new ConversationData("今天天气真啊好哦", "10:23", "愤怒的小明", null));
        mListView.setAdapter(new ConversationAdapter(getActivity(), conversationDataList));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.i(position + "  " + id);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        L.i("contact");
        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }

    private ListView mListView = null;
}
