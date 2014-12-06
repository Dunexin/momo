package com.xin.momo.fragmentTab;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.xin.momo.Adapter.FacePagerAdapter;
import com.xin.momo.Adapter.FacePagerData;
import com.xin.momo.FacePointSelect;
import com.xin.momo.R;
import com.xin.momo.utils.L;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatRoomFaceFragment.OnFaceFragmentInteractionListener} interface
 * to handle interaction events.
 *
 */
public class ChatRoomFaceFragment extends Fragment {

    private OnFaceFragmentInteractionListener mListener;
    private ViewPager mViewPager;
    private FacePagerData mFacePagerData;
    private String []faceWord;
    private FacePointSelect facePointSelect;


    public ChatRoomFaceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        L.i("Face Fragment create");
        return inflater.inflate(R.layout.face_expression_layout, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFaceFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidget();
    }

    private void initWidget(){

        initFaceWord();

        mViewPager = (ViewPager) getActivity().findViewById(R.id.face_view_pager);
        mFacePagerData = new FacePagerData(getActivity());
        mFacePagerData.setOnGridViewItemListener(new OnGridViewItemListener());
        mViewPager.setAdapter(new FacePagerAdapter(mFacePagerData, getActivity()));
        mViewPager.setOnPageChangeListener(new FacePageOnPageChangeListener());
        mViewPager.setCurrentItem(0);

        facePointSelect = new FacePointSelect(getActivity(), mViewPager.getAdapter().getCount());
    }

    private void initFaceWord(){
        new Thread(){

            @Override
            public void run() {
                faceWord = getActivity().getResources().getStringArray(R.array.face_word);
            }
        }.start();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFaceFragmentInteractionListener {

        public void onFragmentInteraction(Uri uri);
        public void FaceInput(long faceId, String faceName, long resource);
        public void FaceFragmentDeleteEven(AdapterView<?> parent, View view, int position, long id);
    }

    class FacePageOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int i) {

            facePointSelect.SetPoint(i);
        }
        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    class OnGridViewItemListener implements FacePagerData.OnGridViewItemListener{

        @Override
        public void onGridViewItemClick(long itemId, long resource) {

            L.i("Face id " + String.valueOf(itemId));
            if (faceWord != null) {
                mListener.FaceInput(itemId, faceWord[((int) itemId)], resource);
            }
        }

        @Override
        public void onDeleteButtonClick(AdapterView<?> parent, View view, int position, long id) {

            mListener.FaceFragmentDeleteEven(parent, view, position, id);
        }
    }
}
