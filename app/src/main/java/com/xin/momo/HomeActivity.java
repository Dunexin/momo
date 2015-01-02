package com.xin.momo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.xin.momo.ActivityCallBack.HomeActivityCallBack;
import com.xin.momo.fragmentTab.ContactFragment;
import com.xin.momo.fragmentTab.ConversationFragment;
import com.xin.momo.fragmentTab.PluginFragment;

public class HomeActivity extends BindCoreServiceFragmentActivity implements OnClickListener,
        ConversationFragment.OnConversationFragmentInteractionListener,
        ContactFragment.OnContactFragmentInteractionListener{

    public final static String ACTIVITY_TAG = "HOME_ACTIVITY";
    private ImageButton mConversation;
    private ImageButton mPlugin;
    private ImageButton mContact;
    private FragmentManager fragmentManager;
    private Fragment activeFragment = null;
    private Fragment mConversationFragment = null;
    private Fragment mPluginFragment = null;
    private Fragment mContactFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initWidget();
        initDefaultFragment();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.home_conversation_button:
                setTabSelection(R.id.home_conversation_button);
                break;
            case R.id.home_contact_button:
                setTabSelection(R.id.home_contact_button);
                break;
            case R.id.home_plugin_button:
                setTabSelection(R.id.home_plugin_button);
                break;
            default:
                break;
        }
    }

    @Override
    public void bindServiceFinish() {

        getCoreService().setActivityCallBack(HomeActivity.ACTIVITY_TAG, new HomeActivityCallBack() {

            @Override
            public void setContactToFragment() {
            }

            @Override
            public void expandableListUpdate() {
                ((ContactFragment)mContactFragment).updateExpandableListView();
            }
        });
    }

    private void setTabSelection(int id){

        mConversation.setBackgroundResource(R.drawable.skin_tab_icon_conversation_normal);
        mContact.setBackgroundResource(R.drawable.skin_tab_icon_contact_normal);
        mPlugin.setBackgroundResource(R.drawable.skin_tab_icon_plugin_normal);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideActiveFragment(fragmentTransaction);

        switch (id){
            case R.id.home_conversation_button:
                mConversation.setBackgroundResource(R.drawable.skin_tab_icon_conversation_selected);
                if(mConversationFragment == null){
                    mConversationFragment = new ConversationFragment();
                    fragmentTransaction.add(R.id.home_activity_content, mConversationFragment);
                }else{
                    fragmentTransaction.show(mConversationFragment);
                }
                activeFragment = mConversationFragment;
                break;
            case R.id.home_contact_button:
                mContact.setBackgroundResource(R.drawable.skin_tab_icon_contact_selected);
                if(mContactFragment == null){
                    mContactFragment = new ContactFragment();
                    fragmentTransaction.add(R.id.home_activity_content, mContactFragment);
                }else{
                    fragmentTransaction.show(mContactFragment);
                }
                activeFragment = mContactFragment;
                break;
            case R.id.home_plugin_button:
                mPlugin.setBackgroundResource(R.drawable.skin_tab_icon_plugin_selected);
                if(mPluginFragment == null){
                    mPluginFragment = new PluginFragment();
                    fragmentTransaction.add(R.id.home_activity_content, mPluginFragment);
                }else{
                    fragmentTransaction.show(mPluginFragment);
                }
                activeFragment = mPluginFragment;
                break;
        }

        fragmentTransaction.commit();
    }


    void hideActiveFragment(FragmentTransaction fragmentTransaction){

        if(activeFragment != null) {
            fragmentTransaction.hide(activeFragment);
        }
    }

    private void initDefaultFragment(){

        fragmentManager = getSupportFragmentManager();
        setTabSelection(R.id.home_conversation_button);
    }

    private void initWidget(){

        mConversation = (ImageButton) findViewById(R.id.home_conversation_button);
        mContact = (ImageButton) findViewById(R.id.home_contact_button);
        mPlugin = (ImageButton) findViewById(R.id.home_plugin_button);

        mContact.setOnClickListener(this);
        mConversation.setOnClickListener(this);
        mPlugin.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {

        Intent i= new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    /*
     * Conversation Fragment callback
     */
    @Override
    public void onConversationFragmentInteraction(Uri uri) {

    }

    @Override
    public void ContactFragmentCreated() {

        ((ContactFragment)mContactFragment).initExpandableListView(getCoreService().getContactListData());
    }
}
