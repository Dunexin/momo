package com.xin.momo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.xin.momo.fragmentTab.ContactFragment;
import com.xin.momo.fragmentTab.ConversationFragment;
import com.xin.momo.fragmentTab.PluginFragment;

public class HomeActivity extends FragmentActivity implements OnClickListener{

    private ImageButton mConversation;
    private ImageButton mPlugin;
    private ImageButton mContact;

    private android.support.v4.app.FragmentManager fragmentManager;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
