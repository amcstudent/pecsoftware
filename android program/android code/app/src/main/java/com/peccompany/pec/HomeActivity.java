package com.peccompany.pec;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.peccompany.pec.HomeActivity.editTextArray;
import static com.peccompany.pec.HomeActivity.homeActivity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int width, height, spinnerSelectedItem = 0;
    static int refreshedData, selectedId, fieldsLength, selectedEditTextIndex, updateData;
    private String searchText = "";
    private TextView[] headerΤextViewArray, textViewArray;
    static EditText[] editTextArray, scrollableEditTextArray;
    static Button updateBtn, cancelBtn, insertBtn;
    private boolean spinnerFirstCall, navigateFromMenu = true;
    static Menu menu, refreshMenu;
    static Spinner spinner, newPositionSpinner;
    static HomeActivity homeActivity;
    static Helper helper;
    static ProgressBar progressBar;
    static ArrayList<CandidateModel> candidatesModel = new ArrayList<CandidateModel>();
    static CompanyModel companyModel;
    static ArrayList<JobOrderModel> jobOrderModel = new ArrayList<JobOrderModel>();
    static ArrayList<ResumeModel> resumesModel = new ArrayList<ResumeModel>();
    static RelativeLayout relativeLayout, horizontalRelativeLayout, verticalRelativeLayout;
    static HorizontalScrollView horizontalScrollView;
    static ScrollView scrollViewTable, scrollView;
    private RelativeLayout.LayoutParams relativeParams;
    static DrawerLayout drawer;
    static JobOrderModel jobDetailsModel;
    static TextView toastMessage;
    static Toast toast;
    static long candidateId, jobOrderId;
    private GradientDrawable gd;
    private static DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeActivity = this;
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#A5A2A2"));
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Helper.hideSoftKeyboard(HomeActivity.this);
                return false;
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //Called when a drawer's position changes.
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // Called when a drawer has settled in a completely closed state.
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // Called when the drawer motion state changes. The new state will be one of STATE_IDLE, STATE_DRAGGING or STATE_SETTLING.
            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        gd = new GradientDrawable();
        gd.setColor(Color.WHITE);
        gd.setCornerRadius(5);
        spinner = (Spinner) findViewById(R.id.spinner);
        scrollViewTable = (ScrollView) findViewById(R.id.tablesv);
        scrollView = (ScrollView) findViewById(R.id.sv);
        scrollView.getLayoutParams().height = height;
        scrollView.getLayoutParams().width = width;
        relativeLayout = (RelativeLayout) findViewById(R.id.rl);
        horizontalRelativeLayout = (RelativeLayout) findViewById(R.id.horizontalrl);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalsv);
        horizontalScrollView.setX(width * 2 / 100);
        horizontalScrollView.setY(height * 20 / 100);
        horizontalScrollView.getLayoutParams().height = height * 97 / 100;
        horizontalScrollView.getLayoutParams().width = width * 96 / 100;
        verticalRelativeLayout = (RelativeLayout) findViewById(R.id.vertical);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        toastMessage = new TextView(this);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setTypeface(null, Typeface.BOLD);
        toastMessage.setPadding(width * 5 / 100, height * 2 / 100, width * 5 / 100, height * 2 / 100);
        toastMessage.setTextSize((float) (width * 1 / 100));
        toast = Toast.makeText(getApplicationContext(), null,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height * 3 / 100);
        menu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);
        helper = new Helper();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getLayoutParams().height = height * 90 / 100;
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimaryDark),
                PorterDuff.Mode.SRC_IN);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            companyModel = (CompanyModel) b.getSerializable("companyModel");
        }
        new GetData().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        refreshMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            if (!Helper.isOnline(homeActivity.getApplicationContext())) {
                toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                toastMessage.setText("No internet connection!");
                toast.setView(toastMessage);
                toast.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);
                builder.setTitle("Confirmation");
                builder.setCancelable(false);
                builder.setMessage("Are you sure you want to refresh data?");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new RefreshData().execute();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id != selectedId) {
            searchText = "";
            navigateFromMenu = true;
            selectedId = id;
            removeSomeViews();
            horizontalScrollView.setY(height * 20 / 100);
            if (id == R.id.nav_add) {
                refreshMenu.getItem(0).setVisible(false);
                relativeLayout.addView(horizontalScrollView);
                insertJob();
            } else if (id == R.id.nav_personal_data) {
                refreshMenu.getItem(0).setVisible(false);
                relativeLayout.addView(horizontalScrollView);
                horizontalRelativeLayout.addView(scrollViewTable);
                horizontalScrollView.setY(height * 2 / 100);
                changeData();
            } else {
                refreshMenu.getItem(0).setVisible(true);
                relativeLayout.addView(horizontalScrollView);
                horizontalRelativeLayout.addView(scrollViewTable);
                navigateMe();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void insertJob() {
        horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
        scrollViewTable.setY(height * 10 / 100);
        scrollViewTable.setX(width * -1 / 100);
        scrollViewTable.getLayoutParams().height = height * 65 / 100;
        scrollViewTable.fullScroll(ScrollView.FOCUS_UP);
        String fieldsName[] = {"*Contact Name", "*Department", "*Billing Contact", "*Phone", "Salary", "*Starting Date", "Address", "*Business type",
                "Placement Fee", "*Placement Date", "*Actual Starting Date", "New Position", "*Educational Requirements", "*Experience Requirements", "Duties", "Bonuses", "*Travel Requirements", "Car",
                "Career Opportunities", "Interview", "Order Taker", "Counselor Ultimate"};
        int x = 5, y = 8;
        textViewArray = new TextView[fieldsName.length];
        editTextArray = new EditText[11];
        scrollableEditTextArray = new EditText[10];
        insertBtn = new Button(this);
        String position[] = {"Yes", "No"};
        newPositionSpinner = new Spinner(this);
        fieldsLength = fieldsName.length;
        for (int i = 0; i < fieldsName.length; i++) {
            textViewArray[i] = new TextView(this);
            relativeLayout.addView(textViewArray[i]);
            relativeParams = (RelativeLayout.LayoutParams) textViewArray[i].getLayoutParams();
            relativeParams.setMargins(width * x / 100, height * y / 100, 0, 0);
            relativeParams.width = width;
            textViewArray[i].setLayoutParams(relativeParams);
            textViewArray[i].setText(fieldsName[i] + " : ");
            textViewArray[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
            textViewArray[i].setTypeface(textViewArray[i].getTypeface(), Typeface.BOLD);

            if (i < 11) {
                editTextArray[i] = new EditText(this);
                relativeLayout.addView(editTextArray[i]);
                relativeParams = (RelativeLayout.LayoutParams) editTextArray[i].getLayoutParams();
                relativeParams.setMargins(width * x / 100, height * (y + 5) / 100, 0, 0);
                relativeParams.width = width * 60 / 100;
                editTextArray[i].setLayoutParams(relativeParams);
                editTextArray[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                editTextArray[i].setSingleLine(true);
                if (i == 6)
                {
                    editTextArray[i].setFocusable(false);
                    editTextArray[i].setText(companyModel.position);
                }
                if (i == 5 || i == 9 || i == 10) {
                    editTextArray[i].setTag(i);
                    editTextArray[i].setFocusable(false);
                    editTextArray[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedEditTextIndex = (int) v.getTag();
                            dateDialog();
                            datePickerDialog.show();
                        }
                    });
                }
            } else if (i == 11) {
                relativeLayout.addView(newPositionSpinner);
                relativeParams = (RelativeLayout.LayoutParams) newPositionSpinner.getLayoutParams();
                relativeParams.setMargins(width * x / 100, height * (y + 5) / 100, 0, 0);
                relativeParams.width = width * 30 / 100;
                relativeParams.height = height * 6 / 100;
                newPositionSpinner.setLayoutParams(relativeParams);
                newPositionSpinner.setBackgroundResource(R.drawable.spinner_border);
                List<String> list = new ArrayList<String>();
                for (int j = 0; j < position.length; j++)
                    list.add(position[j]);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(homeActivity,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                newPositionSpinner.setAdapter(dataAdapter);
            } else {
                scrollableEditTextArray[i - 12] = new EditText(this);
                relativeLayout.addView(scrollableEditTextArray[i - 12]);
                relativeParams = (RelativeLayout.LayoutParams) scrollableEditTextArray[i - 12].getLayoutParams();
                relativeParams.setMargins(width * 1 / 100, height * (y + 5) / 100, 0, 0);
                relativeParams.width = width * 98 / 100;
                relativeParams.height = height * 30 / 100;
                scrollableEditTextArray[i - 12].setLayoutParams(relativeParams);
                scrollableEditTextArray[i - 12].setSingleLine(false);
                scrollableEditTextArray[i - 12].setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                scrollableEditTextArray[i - 12].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                scrollableEditTextArray[i - 12].setVerticalScrollBarEnabled(true);
                scrollableEditTextArray[i - 12].setHorizontalScrollBarEnabled(true);
                scrollableEditTextArray[i - 12].setMovementMethod(new ScrollingMovementMethod());
                scrollableEditTextArray[i - 12].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                scrollableEditTextArray[i - 12].setTextColor(Color.BLACK);
                scrollableEditTextArray[i - 12].setBackgroundResource(R.drawable.border);
                scrollableEditTextArray[i - 12].setGravity(Gravity.TOP);
                scrollableEditTextArray[i - 12].setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                scrollableEditTextArray[i - 12].setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(final View v, final MotionEvent motionEvent) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_UP:
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                        return false;
                    }
                });
            }
            if (i < 12) {
                y += 18;
            } else {
                y += 48;
            }
        }
        relativeLayout.addView(insertBtn);
        relativeParams = (RelativeLayout.LayoutParams) insertBtn.getLayoutParams();
        relativeParams.setMargins(width * 25 / 100, height * y / 100, 0, height * 22 / 100);
        relativeParams.width = width * 50 / 100;
        relativeParams.height = height * 12 / 100;
        insertBtn.setLayoutParams(relativeParams);
        insertBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
        insertBtn.setTypeface(insertBtn.getTypeface(), Typeface.BOLD);
        insertBtn.setText("Insert");
        insertBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!Helper.isOnline(homeActivity.getApplicationContext())) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("No internet connection!");
                    toast.setView(toastMessage);
                    toast.show();
                } else {
                    int error = 0;
                    if (editTextArray[0].getText().toString().trim().length() == 0 || editTextArray[1].getText().toString().trim().length() == 0 || editTextArray[4].getText().toString().trim().length() == 0) {
                        error = 1;
                    }
                    for (int i = 0; i < fieldsLength; i++) {
                        if (error == 1 || error == 2) {
                            break;
                        }
                        if (i < 11) {
                            if (i != 4 && i != 8) {
                                if (editTextArray[i].getText().toString().trim().length() == 0) {
                                    error = 1;
                                } else if (!helper.fieldValidity(editTextArray[i].getText().toString())) {
                                    error = 2;
                                }
                            } else if (i == 4 || i == 8) {
                                if (editTextArray[i].getText().toString().trim().length() > 0) {
                                    if (!editTextArray[i].getText().toString().trim().replace(",", ".").matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                                        error = 2;
                                    }
                                }
                            }
                        } else if (i > 11) {
                            if (!helper.fieldValidity(scrollableEditTextArray[i - 12].getText().toString())) {
                                error = 2;
                            }
                        }
                    }
                    if (error == 1) {
                        toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                        toastMessage.setText("The requirement fields cannot be blank!");
                        toast.setView(toastMessage);
                        toast.show();
                    } else if (error == 2) {
                        toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                        toastMessage.setText("Invalid fields!");
                        toast.setView(toastMessage);
                        toast.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);
                        builder.setTitle("Confirmation");
                        builder.setCancelable(false);
                        builder.setMessage("Are you sure you want to insert the job request?");
                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                new Insert().execute();
                            }
                        });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });
    }

    public void changeData() {
        scrollViewTable.setY(height * 0 / 100);
        scrollViewTable.setX(width * -1 / 100);
        scrollViewTable.getLayoutParams().height = height * 90 / 100;
        scrollViewTable.fullScroll(ScrollView.FOCUS_UP);
        Button[] btns = new Button[2];
        textViewArray = new TextView[3];
        editTextArray = new EditText[3];
        RelativeLayout[] tabs = new RelativeLayout[2];
        int y = 10, x, tabHeight = 0;
        for (int i = 0; i < 2; i++) {
            x = 13;
            tabs[i] = new RelativeLayout(this);
            btns[i] = new Button(this);
            verticalRelativeLayout.addView(tabs[i]);
            tabs[i].addView(btns[i]);

            relativeParams = (RelativeLayout.LayoutParams) tabs[i].getLayoutParams();
            relativeParams.width = width * 99 / 100;
            relativeParams.height = height * 70 / 100;
            relativeParams.setMargins(0, height * tabHeight / 100, 0, height * 9 / 100);
            tabs[i].setLayoutParams(relativeParams);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                tabs[i].setBackgroundDrawable(gd);
            else
                tabs[i].setBackground(gd);

            if (i == 0) {
                textViewArray[0] = new TextView(this);
                editTextArray[0] = new EditText(this);
                textViewArray[1] = new TextView(this);
                editTextArray[1] = new EditText(this);
                tabs[i].addView(textViewArray[0]);
                tabs[i].addView(editTextArray[0]);
                tabs[i].addView(textViewArray[1]);
                tabs[i].addView(editTextArray[1]);
                relativeParams = (RelativeLayout.LayoutParams) textViewArray[0].getLayoutParams();
                relativeParams.width = width;
                relativeParams.height = height * 5 / 100;
                relativeParams.setMargins(width * x / 100, height * y / 100, 0, 0);
                textViewArray[0].setLayoutParams(relativeParams);
                textViewArray[0].setText("Password: ");
                textViewArray[0].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                textViewArray[0].setTypeface(textViewArray[i].getTypeface(), Typeface.BOLD);
                y += 5;

                relativeParams = (RelativeLayout.LayoutParams) editTextArray[0].getLayoutParams();
                relativeParams.width = width * 70 / 100;
                relativeParams.setMargins(width * x / 100, height * y / 100, 0, 0);
                editTextArray[0].setLayoutParams(relativeParams);
                editTextArray[0].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                editTextArray[0].setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextArray[0].setTransformationMethod(PasswordTransformationMethod.getInstance());
                y += 15;

                relativeParams = (RelativeLayout.LayoutParams) textViewArray[1].getLayoutParams();
                relativeParams.width = width;
                relativeParams.height = height * 5 / 100;
                relativeParams.setMargins(width * x / 100, height * y / 100, 0, 0);
                textViewArray[1].setLayoutParams(relativeParams);
                textViewArray[1].setText("Retype Password: ");
                textViewArray[1].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                textViewArray[1].setTypeface(textViewArray[i].getTypeface(), Typeface.BOLD);
                y += 5;

                relativeParams = (RelativeLayout.LayoutParams) editTextArray[1].getLayoutParams();
                relativeParams.width = width * 70 / 100;
                relativeParams.setMargins(width * x / 100, height * y / 100, 0, 0);
                editTextArray[1].setLayoutParams(relativeParams);
                editTextArray[1].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                editTextArray[1].setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextArray[1].setTransformationMethod(PasswordTransformationMethod.getInstance());
                y += 16;

                relativeParams = (RelativeLayout.LayoutParams) btns[0].getLayoutParams();
                relativeParams.width = width * 60 / 100;
                relativeParams.height = height * 10 / 100;
                relativeParams.setMargins(width * 18 / 100, height * y / 100, 0, 0);
                btns[0].setLayoutParams(relativeParams);
                btns[0].setText("Change");
                btns[0].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                btns[0].setTypeface(btns[0].getTypeface(), Typeface.BOLD);
                btns[0].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (!Helper.isOnline(homeActivity.getApplicationContext())) {
                            toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                            toastMessage.setText("No internet connection!");
                            toast.setView(toastMessage);
                            toast.show();
                        } else if (editTextArray[0].getText().toString().trim().length() == 0 || editTextArray[1].getText().toString().trim().length() == 0) {
                            toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                            toastMessage.setText("No empty fields allowed!");
                            toast.setView(toastMessage);
                            toast.show();
                        } else if (!editTextArray[0].getText().toString().trim().equals(editTextArray[1].getText().toString().trim())) {
                            toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                            toastMessage.setText("Passwords don't match!");
                            toast.setView(toastMessage);
                            toast.show();
                        } else if (editTextArray[0].getText().toString().trim().equals(companyModel.pass)) {
                            toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                            toastMessage.setText("The password you entered it's the same with the current password!");
                            toast.setView(toastMessage);
                            toast.show();
                        } else if (!helper.fieldValidity(editTextArray[0].getText().toString())) {
                            toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                            toastMessage.setText("Invalid fields!");
                            toast.setView(toastMessage);
                            toast.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);
                            builder.setTitle("Confirmation");
                            builder.setCancelable(false);
                            builder.setMessage("Are you sure?");
                            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    updateData = 1;
                                    new UpdatePersonalData().execute();
                                }
                            });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
            } else {
                y = 20;
                textViewArray[2] = new TextView(this);
                editTextArray[2] = new EditText(this);
                tabs[i].addView(textViewArray[2]);
                tabs[i].addView(editTextArray[2]);
                relativeParams = (RelativeLayout.LayoutParams) textViewArray[2].getLayoutParams();
                relativeParams.width = width;
                relativeParams.height = height * 5 / 100;
                relativeParams.setMargins(width * x / 100, height * y / 100, 0, 0);
                textViewArray[2].setLayoutParams(relativeParams);
                textViewArray[2].setText("Email: ");
                textViewArray[2].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                textViewArray[2].setTypeface(textViewArray[i].getTypeface(), Typeface.BOLD);
                y += 5;

                relativeParams = (RelativeLayout.LayoutParams) editTextArray[2].getLayoutParams();
                relativeParams.width = width * 70 / 100;
                relativeParams.setMargins(width * x / 100, height * y / 100, 0, 0);
                editTextArray[2].setLayoutParams(relativeParams);
                editTextArray[2].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                editTextArray[2].setSingleLine(true);
                y += 16;

                relativeParams = (RelativeLayout.LayoutParams) btns[1].getLayoutParams();
                relativeParams.width = width * 60 / 100;
                relativeParams.height = height * 10 / 100;
                relativeParams.setMargins(width * 18 / 100, height * y / 100, 0, 0);
                btns[1].setLayoutParams(relativeParams);
                btns[1].setText("Change");
                btns[1].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                btns[1].setTypeface(btns[1].getTypeface(), Typeface.BOLD);
                btns[1].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (!Helper.isOnline(homeActivity.getApplicationContext())) {
                            toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                            toastMessage.setText("No internet connection!");
                            toast.setView(toastMessage);
                            toast.show();
                        } else if (!helper.emailValidity(editTextArray[2].getText().toString().trim())) {
                            toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                            toastMessage.setText("Please enter a valid Email!");
                            toast.setView(toastMessage);
                            toast.show();
                        } else if (editTextArray[2].getText().toString().trim().equals(companyModel.email)) {
                            toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                            toastMessage.setText("The email you entered it's the same with the current email!");
                            toast.setView(toastMessage);
                            toast.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);
                            builder.setTitle("Confirmation");
                            builder.setCancelable(false);
                            builder.setMessage("Are you sure?");
                            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    updateData = 2;
                                    new UpdatePersonalData().execute();
                                }
                            });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
            }
            tabHeight += 80;
        }
    }

    public void jobOrders() {
        horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
        scrollViewTable.setY(height * 10 / 100);
        scrollViewTable.setX(width * -1 / 100);
        scrollViewTable.getLayoutParams().height = height * 65 / 100;
        scrollViewTable.fullScroll(ScrollView.FOCUS_UP);
        TextView[] dateTextArray = new TextView[jobOrderModel.size()];
        TextView[] invoiceNoTextArray = new TextView[jobOrderModel.size()];
        TextView[] departmentTextArray = new TextView[jobOrderModel.size()];
        TextView[] contactNameTextArray = new TextView[jobOrderModel.size()];
        TextView[] phoneTextArray = new TextView[jobOrderModel.size()];
        ImageView[] imageViewDetails = new ImageView[jobOrderModel.size()];
        ImageView[] imageViewDelete = new ImageView[jobOrderModel.size()];

        String[] header = {
                "Date",
                "InvoiceNo",
                "Department",
                "Contact Name",
                "Phone",
        };
        headerΤextViewArray = new TextView[header.length];
        int count = 0, x = 0, y = 0;
        for (int i = 0; i < headerΤextViewArray.length; i++) {
            headerΤextViewArray[i] = new TextView(this);
            horizontalRelativeLayout.addView(headerΤextViewArray[i]);
            relativeParams = (RelativeLayout.LayoutParams) headerΤextViewArray[i].getLayoutParams();
            relativeParams.width = width * 40 / 100;
            relativeParams.height = height * 7 / 100;
            relativeParams.setMargins(width * x / 100, 0, 0, 0);
            headerΤextViewArray[i].setLayoutParams(relativeParams);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                headerΤextViewArray[i].setBackgroundDrawable(gd);
            else
                headerΤextViewArray[i].setBackground(gd);
            headerΤextViewArray[i].setGravity(Gravity.CENTER);
            headerΤextViewArray[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
            headerΤextViewArray[i].setTypeface(null, Typeface.BOLD);
            headerΤextViewArray[i].setTextColor(Color.BLACK);
            headerΤextViewArray[i].setText(header[i]);
            x += 43;
        }
        for (JobOrderModel item : jobOrderModel) {
            x = 0;
            dateTextArray[count] = new TextView(this);
            invoiceNoTextArray[count] = new TextView(this);
            departmentTextArray[count] = new TextView(this);
            contactNameTextArray[count] = new TextView(this);
            phoneTextArray[count] = new TextView(this);
            imageViewDetails[count] = new ImageView(this);
            imageViewDelete[count] = new ImageView(this);

            verticalRelativeLayout.addView(dateTextArray[count]);
            verticalRelativeLayout.addView(invoiceNoTextArray[count]);
            verticalRelativeLayout.addView(departmentTextArray[count]);
            verticalRelativeLayout.addView(contactNameTextArray[count]);
            verticalRelativeLayout.addView(phoneTextArray[count]);
            verticalRelativeLayout.addView(imageViewDetails[count]);
            verticalRelativeLayout.addView(imageViewDelete[count]);

            relativeParams = (RelativeLayout.LayoutParams) dateTextArray[count].getLayoutParams();
            relativeParams.width = width * 40 / 100;
            relativeParams.height = height * 7 / 100;
            relativeParams.setMargins(width * x / 100, height * y / 100, 0, height * 9 / 100);
            dateTextArray[count].setLayoutParams(relativeParams);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                dateTextArray[count].setBackgroundDrawable(gd);
            else
                dateTextArray[count].setBackground(gd);
            dateTextArray[count].setGravity(Gravity.CENTER);
            dateTextArray[count].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
            dateTextArray[count].setTypeface(null, Typeface.BOLD);
            dateTextArray[count].setTextColor(Color.BLACK);
            dateTextArray[count].setText(item.dateInserted);
            x += 43;

            relativeParams = (RelativeLayout.LayoutParams) invoiceNoTextArray[count].getLayoutParams();
            relativeParams.width = width * 40 / 100;
            relativeParams.height = height * 7 / 100;
            relativeParams.setMargins(width * x / 100, height * y / 100, 0, height * 9 / 100);
            invoiceNoTextArray[count].setLayoutParams(relativeParams);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                invoiceNoTextArray[count].setBackgroundDrawable(gd);
            else
                invoiceNoTextArray[count].setBackground(gd);
            invoiceNoTextArray[count].setGravity(Gravity.CENTER);
            invoiceNoTextArray[count].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
            invoiceNoTextArray[count].setTypeface(null, Typeface.BOLD);
            invoiceNoTextArray[count].setTextColor(Color.BLACK);
            invoiceNoTextArray[count].setText("" + item.invoiceNo);
            x += 43;

            relativeParams = (RelativeLayout.LayoutParams) departmentTextArray[count].getLayoutParams();
            relativeParams.width = width * 40 / 100;
            relativeParams.height = height * 7 / 100;
            relativeParams.setMargins(width * x / 100, height * y / 100, 0, height * 9 / 100);
            departmentTextArray[count].setLayoutParams(relativeParams);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                departmentTextArray[count].setBackgroundDrawable(gd);
            else
                departmentTextArray[count].setBackground(gd);
            departmentTextArray[count].setGravity(Gravity.CENTER);
            departmentTextArray[count].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
            departmentTextArray[count].setTypeface(null, Typeface.BOLD);
            departmentTextArray[count].setTextColor(Color.BLACK);
            departmentTextArray[count].setText(item.department);
            x += 43;

            relativeParams = (RelativeLayout.LayoutParams) contactNameTextArray[count].getLayoutParams();
            relativeParams.width = width * 40 / 100;
            relativeParams.height = height * 7 / 100;
            relativeParams.setMargins(width * x / 100, height * y / 100, 0, height * 9 / 100);
            contactNameTextArray[count].setLayoutParams(relativeParams);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                contactNameTextArray[count].setBackgroundDrawable(gd);
            else
                contactNameTextArray[count].setBackground(gd);
            contactNameTextArray[count].setGravity(Gravity.CENTER);
            contactNameTextArray[count].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
            contactNameTextArray[count].setTypeface(null, Typeface.BOLD);
            contactNameTextArray[count].setTextColor(Color.BLACK);
            contactNameTextArray[count].setText(item.contactName);
            x += 43;

            relativeParams = (RelativeLayout.LayoutParams) phoneTextArray[count].getLayoutParams();
            relativeParams.width = width * 40 / 100;
            relativeParams.height = height * 7 / 100;
            relativeParams.setMargins(width * x / 100, height * y / 100, 0, height * 9 / 100);
            phoneTextArray[count].setLayoutParams(relativeParams);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                phoneTextArray[count].setBackgroundDrawable(gd);
            else
                phoneTextArray[count].setBackground(gd);
            phoneTextArray[count].setGravity(Gravity.CENTER);
            phoneTextArray[count].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
            phoneTextArray[count].setTypeface(null, Typeface.BOLD);
            phoneTextArray[count].setTextColor(Color.BLACK);
            phoneTextArray[count].setText(item.phone);
            x += 48;

            relativeParams = (RelativeLayout.LayoutParams) imageViewDetails[count].getLayoutParams();
            relativeParams.width = width * 8 / 100;
            relativeParams.height = height * 5 / 100;
            relativeParams.setMargins(width * x / 100, height * (y + 1) / 100, width * 5 / 100, height * 9 / 100);
            imageViewDetails[count].setLayoutParams(relativeParams);
            imageViewDetails[count].setBackgroundResource(R.drawable.ic_details);
            imageViewDetails[count].setTag(item.jobOrderId);
            imageViewDetails[count].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jobOrderId = (long) v.getTag();
                    relativeLayout.removeAllViews();
                    JobOrderModel model = new JobOrderModel();
                    for (JobOrderModel item : jobOrderModel) {
                        if (item.jobOrderId == jobOrderId) {
                            model = item;
                            break;
                        }
                    }
                    jobOrderDetails(model);
                }
            });
            x += 15;

            relativeParams = (RelativeLayout.LayoutParams) imageViewDelete[count].getLayoutParams();
            relativeParams.width = width * 8 / 100;
            relativeParams.height = height * 5 / 100;
            relativeParams.setMargins(width * x / 100, height * (y + 1) / 100, width * 5 / 100, height * 9 / 100);
            imageViewDelete[count].setLayoutParams(relativeParams);
            imageViewDelete[count].setBackgroundResource(R.drawable.ic_delete);
            imageViewDelete[count].setTag(item.jobOrderId);
            imageViewDelete[count].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (!Helper.isOnline(homeActivity.getApplicationContext())) {
                        toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                        toastMessage.setText("No internet connection!");
                        toast.setView(toastMessage);
                        toast.show();
                    } else {
                        jobOrderId = (long) v.getTag();
                        AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);
                        builder.setTitle("Confirmation");
                        builder.setCancelable(false);
                        builder.setMessage("Are you sure you want to cancel the job request?");
                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                fieldsLength = 0;
                                new Cancel().execute();
                            }
                        });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
            count++;

            y += 8;
        }
    }

    public void candidates() {
        horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
        scrollViewTable.setY(height * 10 / 100);
        scrollViewTable.setX(width * -1 / 100);
        scrollViewTable.getLayoutParams().height = height * 65 / 100;
        scrollViewTable.fullScroll(ScrollView.FOCUS_UP);
        TextView[] nameTextArray = new TextView[candidatesModel.size()];
        TextView[] addressTextArray = new TextView[candidatesModel.size()];
        TextView[] residenceTextArray = new TextView[candidatesModel.size()];
        TextView[] businessTextArray = new TextView[candidatesModel.size()];
        ImageView[] imageViewDetails = new ImageView[candidatesModel.size()];
        ImageView[] imageViewCheck = new ImageView[candidatesModel.size()];
        ImageView[] imageViewDelete = new ImageView[candidatesModel.size()];
        String[] header = {
                "Name",
                "Address",
                "Residence Number",
                "Business Number"
        };
        headerΤextViewArray = new TextView[header.length];
        int count = 0, x = 0, y = 0;
        for (int i = 0; i < headerΤextViewArray.length; i++) {
            headerΤextViewArray[i] = new TextView(this);
            horizontalRelativeLayout.addView(headerΤextViewArray[i]);
            relativeParams = (RelativeLayout.LayoutParams) headerΤextViewArray[i].getLayoutParams();
            relativeParams.width = width * 40 / 100;
            relativeParams.height = height * 7 / 100;
            relativeParams.setMargins(width * x / 100, 0, 0, 0);
            headerΤextViewArray[i].setLayoutParams(relativeParams);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                headerΤextViewArray[i].setBackgroundDrawable(gd);
            else
                headerΤextViewArray[i].setBackground(gd);
            headerΤextViewArray[i].setGravity(Gravity.CENTER);
            headerΤextViewArray[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
            headerΤextViewArray[i].setTypeface(null, Typeface.BOLD);
            headerΤextViewArray[i].setTextColor(Color.BLACK);
            headerΤextViewArray[i].setText(header[i]);
            x += 43;
        }
        for (CandidateModel item : candidatesModel) {
            if ((item.name.toLowerCase().startsWith(searchText.toLowerCase()) && spinnerSelectedItem == 0) || (item.address.toLowerCase().startsWith(searchText.toLowerCase()) && spinnerSelectedItem == 1)) {
                x = 0;
                nameTextArray[count] = new TextView(this);
                addressTextArray[count] = new TextView(this);
                residenceTextArray[count] = new TextView(this);
                businessTextArray[count] = new TextView(this);
                imageViewDetails[count] = new ImageView(this);
                imageViewCheck[count] = new ImageView(this);
                imageViewDelete[count] = new ImageView(this);

                verticalRelativeLayout.addView(nameTextArray[count]);
                verticalRelativeLayout.addView(addressTextArray[count]);
                verticalRelativeLayout.addView(residenceTextArray[count]);
                verticalRelativeLayout.addView(businessTextArray[count]);
                verticalRelativeLayout.addView(imageViewDetails[count]);
                verticalRelativeLayout.addView(imageViewCheck[count]);
                verticalRelativeLayout.addView(imageViewDelete[count]);

                relativeParams = (RelativeLayout.LayoutParams) nameTextArray[count].getLayoutParams();
                relativeParams.width = width * 40 / 100;
                relativeParams.height = height * 7 / 100;
                relativeParams.setMargins(width * x / 100, height * y / 100, 0, height * 9 / 100);
                nameTextArray[count].setLayoutParams(relativeParams);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    nameTextArray[count].setBackgroundDrawable(gd);
                else
                    nameTextArray[count].setBackground(gd);
                nameTextArray[count].setGravity(Gravity.CENTER);
                nameTextArray[count].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
                nameTextArray[count].setTypeface(null, Typeface.BOLD);
                nameTextArray[count].setTextColor(Color.BLACK);
                nameTextArray[count].setText(item.name);
                x += 43;

                relativeParams = (RelativeLayout.LayoutParams) addressTextArray[count].getLayoutParams();
                relativeParams.width = width * 40 / 100;
                relativeParams.height = height * 7 / 100;
                relativeParams.setMargins(width * x / 100, height * y / 100, 0, height * 9 / 100);
                addressTextArray[count].setLayoutParams(relativeParams);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    addressTextArray[count].setBackgroundDrawable(gd);
                else
                    addressTextArray[count].setBackground(gd);
                addressTextArray[count].setGravity(Gravity.CENTER);
                addressTextArray[count].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
                addressTextArray[count].setTypeface(null, Typeface.BOLD);
                addressTextArray[count].setTextColor(Color.BLACK);
                addressTextArray[count].setText("" + item.address);
                x += 43;

                relativeParams = (RelativeLayout.LayoutParams) residenceTextArray[count].getLayoutParams();
                relativeParams.width = width * 40 / 100;
                relativeParams.height = height * 7 / 100;
                relativeParams.setMargins(width * x / 100, height * y / 100, 0, height * 9 / 100);
                residenceTextArray[count].setLayoutParams(relativeParams);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    residenceTextArray[count].setBackgroundDrawable(gd);
                else
                    residenceTextArray[count].setBackground(gd);
                residenceTextArray[count].setGravity(Gravity.CENTER);
                residenceTextArray[count].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
                residenceTextArray[count].setTypeface(null, Typeface.BOLD);
                residenceTextArray[count].setTextColor(Color.BLACK);
                residenceTextArray[count].setText(item.residenceNumber);
                x += 43;

                relativeParams = (RelativeLayout.LayoutParams) businessTextArray[count].getLayoutParams();
                relativeParams.width = width * 40 / 100;
                relativeParams.height = height * 7 / 100;
                relativeParams.setMargins(width * x / 100, height * y / 100, 0, height * 9 / 100);
                businessTextArray[count].setLayoutParams(relativeParams);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    businessTextArray[count].setBackgroundDrawable(gd);
                else
                    businessTextArray[count].setBackground(gd);
                businessTextArray[count].setGravity(Gravity.CENTER);
                businessTextArray[count].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
                businessTextArray[count].setTypeface(null, Typeface.BOLD);
                businessTextArray[count].setTextColor(Color.BLACK);
                businessTextArray[count].setText(item.businessNumber);
                x += 48;

                relativeParams = (RelativeLayout.LayoutParams) imageViewDetails[count].getLayoutParams();
                relativeParams.width = width * 8 / 100;
                relativeParams.height = height * 5 / 100;
                relativeParams.setMargins(width * x / 100, height * (y + 1) / 100, width * 5 / 100, height * 9 / 100);
                imageViewDetails[count].setLayoutParams(relativeParams);
                imageViewDetails[count].setBackgroundResource(R.drawable.ic_details);
                imageViewDetails[count].setTag(item.candidateId);
                imageViewDetails[count].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        candidateId = (long) v.getTag();
                        relativeLayout.removeAllViews();
                        CandidateModel model = new CandidateModel();
                        for (CandidateModel item : candidatesModel) {
                            if (item.candidateId == candidateId) {
                                model = item;
                                break;
                            }
                        }
                        candidateDetails(model);
                    }
                });
                x += 15;

                relativeParams = (RelativeLayout.LayoutParams) imageViewCheck[count].getLayoutParams();
                relativeParams.width = width * 8 / 100;
                relativeParams.height = height * 5 / 100;
                relativeParams.setMargins(width * x / 100, height * (y + 1) / 100, width * 5 / 100, height * 9 / 100);
                imageViewCheck[count].setLayoutParams(relativeParams);
                imageViewCheck[count].setBackgroundResource(R.drawable.ic_check);
                imageViewCheck[count].setTag(item.candidateId);
                imageViewCheck[count].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        candidateId = (long) v.getTag();
                        createTableForChecking();
                    }
                });
                x += 15;

                relativeParams = (RelativeLayout.LayoutParams) imageViewDelete[count].getLayoutParams();
                relativeParams.width = width * 8 / 100;
                relativeParams.height = height * 5 / 100;
                relativeParams.setMargins(width * x / 100, height * (y + 1) / 100, width * 5 / 100, height * 9 / 100);
                imageViewDelete[count].setLayoutParams(relativeParams);
                imageViewDelete[count].setBackgroundResource(R.drawable.ic_delete);
                imageViewDelete[count].setTag(item.candidateId);
                imageViewDelete[count].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (!Helper.isOnline(homeActivity.getApplicationContext())) {
                            toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                            toastMessage.setText("No internet connection!");
                            toast.setView(toastMessage);
                            toast.show();
                        } else {
                            candidateId = (long) v.getTag();
                            AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);
                            builder.setTitle("Confirmation");
                            builder.setCancelable(false);
                            builder.setMessage("Are you sure?");
                            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    removeSomeViews();
                                    relativeLayout.removeAllViews();
                                    new Delete().execute();
                                }
                            });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
                count++;

                y += 8;
            }
        }
    }

    private void candidateDetails(CandidateModel modelDetails) {
        String fieldsName[] = {"Name", "Address", "Birthdate", "Residence Number", "Business Number", "Driver Information", "Degree",
                "Position Desired", "Geographic Preference", "Salary Desired", "Current Position Salary", "One Previous Position Salary",
                "Two Previous Position Salary", "Three Previous Position Salary", "Ratings", "Tenure Responsibilities", "Marital Status", "Travel Preference",
                "Leaving Reason", "Interview Impressions", "Consultant Initials"};
        int x = 2, y = 15;
        ImageView imageViewBack = new ImageView(this);
        relativeLayout.addView(imageViewBack);
        relativeParams = (RelativeLayout.LayoutParams) imageViewBack.getLayoutParams();
        relativeParams.width = width * 12 / 100;
        relativeParams.height = height * 7 / 100;
        relativeParams.setMargins(width * 2 / 100, height * 1 / 100, 0, 0);
        imageViewBack.setLayoutParams(relativeParams);
        imageViewBack.setBackgroundResource(R.drawable.ic_arrow_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSomeViews();
                relativeLayout.addView(horizontalScrollView);
                horizontalRelativeLayout.addView(scrollViewTable);
                navigateMe();
            }
        });
        ArrayList<String> values = helper.modelCandidateDetailsValues(modelDetails);
        textViewArray = new TextView[fieldsName.length];
        TextView[] scrollabletextViewArray = new TextView[3];
        for (int i = 0; i < fieldsName.length; i++) {
            String value = "";

            textViewArray[i] = new TextView(this);
            relativeLayout.addView(textViewArray[i]);
            relativeParams = (RelativeLayout.LayoutParams) textViewArray[i].getLayoutParams();
            relativeParams.setMargins(width * x / 100, height * y / 100, 0, 0);
            relativeParams.width = width;
            textViewArray[i].setLayoutParams(relativeParams);
            textViewArray[i].setText(fieldsName[i] + " : ");
            textViewArray[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
            textViewArray[i].setTypeface(textViewArray[i].getTypeface(), Typeface.BOLD);

            if (i >= 18) {
                scrollabletextViewArray[i - 18] = new TextView(this);
                relativeLayout.addView(scrollabletextViewArray[i - 18]);
                relativeParams = (RelativeLayout.LayoutParams) scrollabletextViewArray[i - 18].getLayoutParams();
                if (i == fieldsName.length - 1)
                    relativeParams.setMargins(width * x / 100, height * (y + 5) / 100, 0, height * 17 / 100);
                else
                    relativeParams.setMargins(width * x / 100, height * (y + 5) / 100, 0, 0);
                relativeParams.width = width * 96 / 100;
                relativeParams.height = height * 30 / 100;
                scrollabletextViewArray[i - 18].setLayoutParams(relativeParams);
                value = values.get(i).replaceAll("@", "\n");
                scrollabletextViewArray[i - 18].setSingleLine(false);
                scrollabletextViewArray[i - 18].setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                scrollabletextViewArray[i - 18].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                scrollabletextViewArray[i - 18].setVerticalScrollBarEnabled(true);
                scrollabletextViewArray[i - 18].setHorizontalScrollBarEnabled(true);
                scrollabletextViewArray[i - 18].setMovementMethod(new ScrollingMovementMethod());
                scrollabletextViewArray[i - 18].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                scrollabletextViewArray[i - 18].setTextColor(Color.BLACK);
                scrollabletextViewArray[i - 18].setBackgroundResource(R.drawable.border);
                scrollabletextViewArray[i - 18].setGravity(Gravity.TOP);
                scrollabletextViewArray[i - 18].setText(value);
                scrollabletextViewArray[i - 18].setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                scrollabletextViewArray[i - 18].setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(final View v, final MotionEvent motionEvent) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_UP:
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                        return false;
                    }
                });
            } else {
                if (values.get(i).equals("0.0")) {
                    value = "-";
                } else {
                    value = values.get(i);
                }
                if (i == 14) {
                    value += "/5";
                }
                textViewArray[i].setText(textViewArray[i].getText() + value);
            }
            if (i < 18) {
                y += 15;
            } else {
                y += height * 6 / 100;
            }
        }
    }

    public void jobOrderDetails(JobOrderModel modelDetails) {
        jobDetailsModel = modelDetails;
        String fieldsName[] = {"*Contact Name", "*Department", "*Billing Contact", "*Phone", "Salary", "*Starting Date", "Address", "*Business type",
                "Placement Fee", "*Placement Date", "*Actual Starting Date", "New Position", "*Educational Requirements", "*Experience Requirements", "Duties", "Bonuses", "*Travel Requirements", "Car",
                "Career Opportunities", "Interview", "Order Taker", "Counselor Ultimate"};
        int x = 5, y = 14;
        ImageView imageViewBack = new ImageView(this);
        relativeLayout.addView(imageViewBack);
        relativeParams = (RelativeLayout.LayoutParams) imageViewBack.getLayoutParams();
        relativeParams.width = width * 12 / 100;
        relativeParams.height = height * 7 / 100;
        relativeParams.setMargins(width * 2 / 100, height * 1 / 100, 0, 0);
        imageViewBack.setLayoutParams(relativeParams);
        imageViewBack.setBackgroundResource(R.drawable.ic_arrow_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSomeViews();
                relativeLayout.addView(horizontalScrollView);
                horizontalRelativeLayout.addView(scrollViewTable);
                navigateMe();
            }
        });

        ArrayList<String> values = helper.modelJobDetailsValues(modelDetails);
        textViewArray = new TextView[fieldsName.length];
        editTextArray = new EditText[11];
        scrollableEditTextArray = new EditText[10];
        updateBtn = new Button(this);
        cancelBtn = new Button(this);
        String position[] = {"Yes", "No"};
        newPositionSpinner = new Spinner(this);
        fieldsLength = fieldsName.length;
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, position);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        newPositionSpinner.setAdapter(spinnerArrayAdapter);
        for (int i = 0; i < fieldsName.length; i++) {
            String value = "";

            textViewArray[i] = new TextView(this);
            relativeLayout.addView(textViewArray[i]);
            relativeParams = (RelativeLayout.LayoutParams) textViewArray[i].getLayoutParams();
            relativeParams.setMargins(width * x / 100, height * y / 100, 0, 0);
            relativeParams.width = width;
            textViewArray[i].setLayoutParams(relativeParams);
            textViewArray[i].setText(fieldsName[i] + " : ");
            textViewArray[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
            textViewArray[i].setTypeface(textViewArray[i].getTypeface(), Typeface.BOLD);

            if (i < 11) {
                if (!values.get(i).equals("0.0")) {
                    value = values.get(i);
                }
                editTextArray[i] = new EditText(this);
                relativeLayout.addView(editTextArray[i]);
                relativeParams = (RelativeLayout.LayoutParams) editTextArray[i].getLayoutParams();
                relativeParams.setMargins(width * x / 100, height * (y + 5) / 100, 0, 0);
                relativeParams.width = width * 60 / 100;
                editTextArray[i].setLayoutParams(relativeParams);
                editTextArray[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                editTextArray[i].setText(value);
                if (i == 6) {
                    editTextArray[i].setFocusable(false);
                    editTextArray[i].setText(companyModel.position);
                }
            } else if (i == 11) {
                value = values.get(i);
                relativeLayout.addView(newPositionSpinner);
                relativeParams = (RelativeLayout.LayoutParams) newPositionSpinner.getLayoutParams();
                relativeParams.setMargins(width * x / 100, height * (y + 5) / 100, 0, 0);
                relativeParams.width = width * 30 / 100;
                relativeParams.height = height * 6 / 100;
                newPositionSpinner.setLayoutParams(relativeParams);
                newPositionSpinner.setBackgroundResource(R.drawable.spinner_border);
                List<String> list = new ArrayList<String>();
                int selectedIndex = 0;
                for (int j = 0; j < position.length; j++) {
                    list.add(position[j]);
                    if (position[j].equals(value))
                        selectedIndex = j;
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(homeActivity,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                newPositionSpinner.setAdapter(dataAdapter);
                newPositionSpinner.setSelection(selectedIndex);
            } else {
                scrollableEditTextArray[i - 12] = new EditText(this);
                relativeLayout.addView(scrollableEditTextArray[i - 12]);
                relativeParams = (RelativeLayout.LayoutParams) scrollableEditTextArray[i - 12].getLayoutParams();
                relativeParams.setMargins(width * 1 / 100, height * (y + 5) / 100, 0, 0);
                relativeParams.width = width * 98 / 100;
                relativeParams.height = height * 30 / 100;
                scrollableEditTextArray[i - 12].setLayoutParams(relativeParams);
                value = values.get(i).replaceAll("@", "\n");
                scrollableEditTextArray[i - 12].setSingleLine(false);
                scrollableEditTextArray[i - 12].setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                scrollableEditTextArray[i - 12].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                scrollableEditTextArray[i - 12].setVerticalScrollBarEnabled(true);
                scrollableEditTextArray[i - 12].setHorizontalScrollBarEnabled(true);
                scrollableEditTextArray[i - 12].setMovementMethod(new ScrollingMovementMethod());
                scrollableEditTextArray[i - 12].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
                scrollableEditTextArray[i - 12].setTextColor(Color.BLACK);
                scrollableEditTextArray[i - 12].setBackgroundResource(R.drawable.border);
                scrollableEditTextArray[i - 12].setGravity(Gravity.TOP);
                scrollableEditTextArray[i - 12].setText(value);
                scrollableEditTextArray[i - 12].setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                scrollableEditTextArray[i - 12].setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(final View v, final MotionEvent motionEvent) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_UP:
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                        return false;
                    }
                });
            }
            if (i < 12) {
                y += 18;
            } else {
                y += 48;
            }
        }
        relativeLayout.addView(updateBtn);
        relativeParams = (RelativeLayout.LayoutParams) updateBtn.getLayoutParams();
        relativeParams.setMargins(width * 6 / 100, height * (y + 10) / 100, 0, height * 25 / 100);
        relativeParams.width = width * 40 / 100;
        relativeParams.height = height * 20 / 100;
        updateBtn.setLayoutParams(relativeParams);
        updateBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
        updateBtn.setTypeface(updateBtn.getTypeface(), Typeface.BOLD);
        updateBtn.setText("Update");
        updateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!Helper.isOnline(homeActivity.getApplicationContext())) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("No internet connection!");
                    toast.setView(toastMessage);
                    toast.show();
                } else {
                    int error = 0;
                    if (editTextArray[0].getText().toString().trim().length() == 0 || editTextArray[1].getText().toString().trim().length() == 0 || editTextArray[4].getText().toString().trim().length() == 0) {
                        error = 1;
                    }
                    for (int i = 0; i < fieldsLength; i++) {
                        if (error == 1 || error == 2) {
                            break;
                        }
                        if (i < 11) {
                            if (i != 4 && i != 8) {
                                if (editTextArray[i].getText().toString().trim().length() == 0) {
                                    error = 1;
                                } else if (!helper.fieldValidity(editTextArray[i].getText().toString())) {
                                    error = 2;
                                }
                            } else if (i == 4 || i == 8) {
                                if (editTextArray[i].getText().toString().trim().length() > 0) {
                                    if (!editTextArray[i].getText().toString().trim().replace(",", ".").matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                                        error = 2;
                                    }
                                }
                            }
                        } else if (i > 11) {
                            if (!helper.fieldValidity(editTextArray[i - 12].getText().toString())) {
                                error = 2;
                            }
                        }
                    }
                    if (error == 1) {
                        toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                        toastMessage.setText("The requirement fields cannot be blank!");
                        toast.setView(toastMessage);
                        toast.show();
                    } else if (error == 2) {
                        toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                        toastMessage.setText("Invalid fields!");
                        toast.setView(toastMessage);
                        toast.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);
                        builder.setTitle("Confirmation");
                        builder.setCancelable(false);
                        builder.setMessage("Are you sure you want to update the job request?");
                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                new Update().execute();
                            }
                        });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });

        relativeLayout.addView(cancelBtn);
        relativeParams = (RelativeLayout.LayoutParams) cancelBtn.getLayoutParams();
        relativeParams.setMargins(width * 53 / 100, height * (y + 10) / 100, 0, height * 25 / 100);
        relativeParams.width = width * 40 / 100;
        relativeParams.height = height * 20 / 100;
        cancelBtn.setLayoutParams(relativeParams);
        cancelBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 5 / 100);
        cancelBtn.setTypeface(cancelBtn.getTypeface(), Typeface.BOLD);
        cancelBtn.setText("Cancel");
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!Helper.isOnline(homeActivity.getApplicationContext())) {
                    toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                    toastMessage.setText("No internet connection!");
                    toast.setView(toastMessage);
                    toast.show();
                } else {
                    jobOrderId = jobDetailsModel.jobOrderId;
                    AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);
                    builder.setTitle("Confirmation");
                    builder.setCancelable(false);
                    builder.setMessage("Are you sure you want to cancel the job request?");
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new Cancel().execute();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    private void createTableForChecking() {
        if (!Helper.isOnline(homeActivity.getApplicationContext())) {
            toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
            toastMessage.setText("No internet connection!");
            toast.setView(toastMessage);
            toast.show();
        } else {
            int count = 0, x = 18, y = 0;
            removeSomeViews();
            relativeLayout.addView(horizontalScrollView);
            horizontalRelativeLayout.addView(scrollViewTable);
            ImageView imageViewBack = new ImageView(homeActivity);
            relativeLayout.addView(imageViewBack);
            relativeParams = (RelativeLayout.LayoutParams) imageViewBack.getLayoutParams();
            relativeParams.width = width * 12 / 100;
            relativeParams.height = height * 7 / 100;
            relativeParams.setMargins(width * 2 / 100, height * 3 / 100, 0, 0);
            imageViewBack.setLayoutParams(relativeParams);
            imageViewBack.setBackgroundResource(R.drawable.ic_arrow_back);
            imageViewBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeSomeViews();
                    relativeLayout.addView(horizontalScrollView);
                    horizontalRelativeLayout.addView(scrollViewTable);
                    navigateMe();
                }
            });
            String[] header = {
                    "Department"
            };
            headerΤextViewArray = new TextView[header.length];
            for (int i = 0; i < headerΤextViewArray.length; i++) {
                headerΤextViewArray[i] = new TextView(homeActivity);
                horizontalRelativeLayout.addView(headerΤextViewArray[i]);
                relativeParams = (RelativeLayout.LayoutParams) headerΤextViewArray[i].getLayoutParams();
                relativeParams.width = width * 60 / 100;
                relativeParams.height = height * 7 / 100;
                relativeParams.setMargins(width * x / 100, 0, 0, 0);
                headerΤextViewArray[i].setLayoutParams(relativeParams);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    headerΤextViewArray[i].setBackgroundDrawable(gd);
                else
                    headerΤextViewArray[i].setBackground(gd);
                headerΤextViewArray[i].setGravity(Gravity.CENTER);
                headerΤextViewArray[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
                headerΤextViewArray[i].setTypeface(null, Typeface.BOLD);
                headerΤextViewArray[i].setTextColor(Color.BLACK);
                headerΤextViewArray[i].setText(header[i]);
            }
            textViewArray = new TextView[jobOrderModel.size()];
            ImageView[] imageViews = new ImageView[jobOrderModel.size()];
            for (JobOrderModel item : jobOrderModel) {
                x = 18;
                textViewArray[count] = new TextView(homeActivity);
                imageViews[count] = new ImageView(homeActivity);
                verticalRelativeLayout.addView(textViewArray[count]);
                verticalRelativeLayout.addView(imageViews[count]);

                relativeParams = (RelativeLayout.LayoutParams) textViewArray[count].getLayoutParams();
                relativeParams.width = width * 60 / 100;
                relativeParams.height = height * 7 / 100;
                relativeParams.setMargins(width * x / 100, height * y / 100, 0, height * 9 / 100);
                textViewArray[count].setLayoutParams(relativeParams);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    textViewArray[count].setBackgroundDrawable(gd);
                else
                    textViewArray[count].setBackground(gd);
                textViewArray[count].setGravity(Gravity.CENTER);
                textViewArray[count].setTextSize(TypedValue.COMPLEX_UNIT_PX, width * 4 / 100);
                textViewArray[count].setTypeface(null, Typeface.BOLD);
                textViewArray[count].setTextColor(Color.BLACK);
                textViewArray[count].setText(item.department);
                x += 66;

                relativeParams = (RelativeLayout.LayoutParams) imageViews[count].getLayoutParams();
                relativeParams.width = width * 8 / 100;
                relativeParams.height = height * 5 / 100;
                relativeParams.setMargins(width * x / 100, height * (y + 1) / 100, width * 5 / 100, height * 9 / 100);
                imageViews[count].setLayoutParams(relativeParams);
                imageViews[count].setBackgroundResource(R.drawable.ic_check);
                imageViews[count].setTag(item.jobOrderId);
                imageViews[count].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Helper.isOnline(homeActivity.getApplicationContext())) {
                            toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
                            toastMessage.setText("No internet connection!");
                            toast.setView(toastMessage);
                            toast.show();
                        } else {
                            jobOrderId = (long) v.getTag();
                            AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);
                            builder.setTitle("Confirmation");
                            builder.setCancelable(false);
                            builder.setMessage("Are you sure?");
                            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new Check().execute();
                                }
                            });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
                count++;

                y += 8;
            }
        }
    }

    public void removeSomeViews() {
        relativeLayout.removeAllViews();
        horizontalRelativeLayout.removeAllViews();
        verticalRelativeLayout.removeAllViews();
    }

    public void createSearchViewAndSpinner() {
        SearchView searchView = new SearchView(homeActivity);
        relativeLayout.addView(searchView);
        relativeParams = (RelativeLayout.LayoutParams) searchView.getLayoutParams();
        relativeParams.setMargins(width * -2 / 100, height * 5 / 100, 0, height * 5 / 100);
        relativeParams.width = width * 60 / 100;
        searchView.setLayoutParams(relativeParams);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search");
        searchView.setQuery(searchText, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                if (newText.length() == 0) {
                    removeSomeViews();
                    relativeLayout.addView(horizontalScrollView);
                    horizontalRelativeLayout.addView(scrollViewTable);
                    navigateMe();
                }
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchText = query;
                removeSomeViews();
                relativeLayout.addView(horizontalScrollView);
                horizontalRelativeLayout.addView(scrollViewTable);
                navigateMe();
                return false;
            }


        });

        relativeLayout.addView(spinner);
        relativeParams = (RelativeLayout.LayoutParams) spinner.getLayoutParams();
        relativeParams.setMargins(width * 60 / 100, height * 6 / 100, 0, 0);
        relativeParams.width = width;
        relativeParams.height = height * 6 / 100;
        spinner.setLayoutParams(relativeParams);
        List<String> list = new ArrayList<String>();
        String[] searchby;
        if (selectedId == R.id.nav_job_orders)
            searchby = new String[]{"Date", "InvoiceNo", "Department"};
        else
            searchby = new String[]{"Name", "Address"};
        for (int i = 0; i < searchby.length; i++)
            list.add(searchby[i]);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(homeActivity,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        if (navigateFromMenu) {
            if (spinner.getSelectedItemPosition() != 0) {
                spinner.setSelection(0);
            }
        } else
            spinner.setSelection(spinnerSelectedItem);
        spinnerFirstCall = true;
        navigateFromMenu = false;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (!spinnerFirstCall && spinnerSelectedItem != position) {
                    spinnerSelectedItem = position;
                    removeSomeViews();
                    relativeLayout.addView(horizontalScrollView);
                    horizontalRelativeLayout.addView(scrollViewTable);
                    navigateMe();
                }
                spinnerFirstCall = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void navigateMe() {
        if (homeActivity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) homeActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(homeActivity.getCurrentFocus().getWindowToken(), 0);
        }
        createSearchViewAndSpinner();
        if (selectedId == R.id.nav_job_orders) {
            jobOrders();
        } else {
            candidates();
        }
    }

    private static void dateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(homeActivity, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editTextArray[selectedEditTextIndex].setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}

class GetData extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {
        homeActivity.progressBar.setVisibility(View.VISIBLE);
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    protected String doInBackground(Void... arg0) {
        try {
            String urlString = homeActivity.helper.getDomainName(homeActivity) + "clientsJobOrders.php?id=" + homeActivity.companyModel.id;
            homeActivity.jobOrderModel = homeActivity.helper.getJobOrders(urlString);
            urlString = homeActivity.helper.getDomainName(homeActivity) + "candidatesFound.php?id=" + homeActivity.companyModel.id;
            homeActivity.candidatesModel = homeActivity.helper.getCandidates(urlString);
            urlString = homeActivity.helper.getDomainName(homeActivity) + "candidatesResumes.php?id=" + homeActivity.companyModel.id;
            homeActivity.resumesModel = homeActivity.helper.getResumes(urlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Done";
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        homeActivity.selectedId = R.id.nav_job_orders;
        homeActivity.menu.getItem(0).setChecked(true);
        homeActivity.menu.getItem(0).setTitle(homeActivity.menu.getItem(0).getTitle() + " (" + homeActivity.jobOrderModel.size() + ")");
        homeActivity.menu.getItem(1).setTitle(homeActivity.menu.getItem(1).getTitle() + " (" + homeActivity.candidatesModel.size() + ")");
        homeActivity.progressBar.setVisibility(View.INVISIBLE);
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        homeActivity.relativeLayout.removeView(homeActivity.spinner);
        homeActivity.createSearchViewAndSpinner();
        homeActivity.jobOrders();
        super.onPostExecute(result);
    }
}

class Insert extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {
        homeActivity.removeSomeViews();
        homeActivity.relativeLayout.addView(homeActivity.progressBar);
        homeActivity.progressBar.setVisibility(View.VISIBLE);
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    protected String doInBackground(Void... arg0) {
        int i;
        String parameterFields[] = {"contactName", "department", "billingContact", "phone", "salary", "startingDate", "address", "businessType",
                "placementFee", "placementDate", "actualStartingDate", "newPosition", "educationalRequirements", "experienceRequirements", "duties", "bonuses", "travelRequirements", "car",
                "careerOpportunities", "interview", "orderTaker", "counselorUltimatePlacement"};
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String urlParameters = "position=" + homeActivity.companyModel.position + "&dateInserted=" + df.format(Calendar.getInstance().getTime());
        for (i = 0; i < homeActivity.fieldsLength; i++) {
            if (i < 11) {
                homeActivity.editTextArray[i].setEnabled(false);
                if (i == 4 || i == 8) {
                    urlParameters += "&" + parameterFields[i] + "=" + homeActivity.editTextArray[i].getText().toString().trim().replace(",", ".");
                } else {
                    urlParameters += "&" + parameterFields[i] + "=" + homeActivity.editTextArray[i].getText().toString().trim();
                }
            } else if (i == 11) {
                homeActivity.newPositionSpinner.setEnabled(false);
                urlParameters += "&" + parameterFields[i] + "=" + homeActivity.newPositionSpinner.getSelectedItem();
            } else {
                homeActivity.scrollableEditTextArray[i - 12].setEnabled(false);
                urlParameters += "&" + parameterFields[i] + "=" + homeActivity.scrollableEditTextArray[i - 12].getText().toString().trim().replaceAll("\\n", "@");
            }
        }
        String url = homeActivity.helper.getDomainName(homeActivity) + "insertJob.php";
        homeActivity.companyModel.maxInvoiceNo++;
        urlParameters += "&invoiceNo=" + homeActivity.companyModel.maxInvoiceNo + "&firm=" + homeActivity.companyModel.name + "&clientId=" + homeActivity.companyModel.id;
        String response = homeActivity.helper.postMethod(url, urlParameters);
        return response;
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        double salary, placementFee;
        if (homeActivity.editTextArray[4].getText().toString().trim().length() == 0) {
            salary = 0;
        } else {
            salary = Double.parseDouble(homeActivity.editTextArray[4].getText().toString().trim().replace(",", "."));
        }
        if (homeActivity.editTextArray[8].getText().toString().trim().length() == 0) {
            placementFee = 0;
        } else {
            placementFee = Double.parseDouble(homeActivity.editTextArray[8].getText().toString().trim().replace(",", "."));
        }
        homeActivity.removeSomeViews();
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        if (result.charAt(0) == 'A') {
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
            homeActivity.toastMessage.setText(result);
            homeActivity.toast.setView(homeActivity.toastMessage);
            homeActivity.toast.show();
            homeActivity.relativeLayout.addView(homeActivity.horizontalScrollView);
            homeActivity.horizontalRelativeLayout.addView(homeActivity.scrollViewTable);
            homeActivity.insertJob();
        } else {
            JobOrderModel temp = new JobOrderModel();
            temp.jobOrderId = Long.parseLong(result);
            temp.position = homeActivity.companyModel.position;
            temp.dateInserted = df.format(Calendar.getInstance().getTime());
            temp.contactName = homeActivity.editTextArray[0].getText().toString().trim();
            temp.department = homeActivity.editTextArray[1].getText().toString().trim();
            temp.billingContact = homeActivity.editTextArray[2].getText().toString().trim();
            temp.phone = homeActivity.editTextArray[3].getText().toString().trim();
            temp.salary = salary;
            temp.startingDate = homeActivity.editTextArray[5].getText().toString().trim();
            temp.address = homeActivity.editTextArray[6].getText().toString().trim();
            temp.businessType = homeActivity.editTextArray[7].getText().toString().trim();
            temp.placementFee = placementFee;
            temp.placementDate = homeActivity.editTextArray[9].getText().toString().trim();
            temp.actualStartingDate = homeActivity.editTextArray[10].getText().toString().trim();
            temp.newPosition = homeActivity.newPositionSpinner.getSelectedItem().toString();
            temp.educationalRequirements = homeActivity.scrollableEditTextArray[0].getText().toString().trim().replaceAll("\\n", "@");
            temp.experienceRequirements = homeActivity.scrollableEditTextArray[1].getText().toString().trim().replaceAll("\\n", "@");
            temp.duties = homeActivity.scrollableEditTextArray[2].getText().toString().trim().replaceAll("\\n", "@");
            temp.bonuses = homeActivity.scrollableEditTextArray[3].getText().toString().trim().replaceAll("\\n", "@");
            temp.travelRequirements = homeActivity.scrollableEditTextArray[4].getText().toString().replaceAll("\\n", "@");
            temp.car = homeActivity.scrollableEditTextArray[5].getText().toString().trim().replaceAll("\\n", "@");
            temp.careerOpportunities = homeActivity.scrollableEditTextArray[6].getText().toString().trim().replaceAll("\\n", "@");
            temp.interview = homeActivity.scrollableEditTextArray[7].getText().toString().trim().replaceAll("\\n", "@");
            temp.orderTaker = homeActivity.scrollableEditTextArray[8].getText().toString().trim().replaceAll("\\n", "@");
            temp.counselorUltimate = homeActivity.scrollableEditTextArray[9].getText().toString().trim().replaceAll("\\n", "@");
            temp.invoiceNo = homeActivity.companyModel.maxInvoiceNo;
            homeActivity.jobOrderModel.add(temp);
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#038E18"));
            homeActivity.toastMessage.setText("Your job request has been inserted successfully!");
            homeActivity.toast.setView(homeActivity.toastMessage);
            homeActivity.toast.show();
            homeActivity.selectedId = R.id.nav_job_orders;
            homeActivity.menu.getItem(0).setChecked(true);
            homeActivity.relativeLayout.addView(homeActivity.horizontalScrollView);
            homeActivity.horizontalRelativeLayout.addView(homeActivity.scrollViewTable);
            homeActivity.navigateMe();
        }
        super.onPostExecute(result);
    }
}

class Update extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {
        homeActivity.removeSomeViews();
        homeActivity.relativeLayout.addView(homeActivity.progressBar);
        homeActivity.progressBar.setVisibility(View.VISIBLE);
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    protected String doInBackground(Void... arg0) {
        int i;
        String parameterFields[] = {"contactName", "department", "billingContact", "phone", "salary", "startingDate", "address", "businessType",
                "placementFee", "placementDate", "actualStartingDate", "newPosition", "educationalRequirements", "experienceRequirements", "duties", "bonuses", "travelRequirements", "car",
                "careerOpportunities", "interview", "orderTaker", "counselorUltimatePlacement"};
        String url = homeActivity.helper.getDomainName(homeActivity) + "updateJob.php";
        String urlParameters = "jobId=" + homeActivity.jobDetailsModel.jobOrderId;
        for (i = 0; i < homeActivity.fieldsLength; i++) {
            if (i < 11) {
                homeActivity.editTextArray[i].setEnabled(false);
                if (i == 4 || i == 8) {
                    urlParameters += "&" + parameterFields[i] + "=" + homeActivity.editTextArray[i].getText().toString().trim().replace(",", ".");
                } else {
                    urlParameters += "&" + parameterFields[i] + "=" + homeActivity.editTextArray[i].getText().toString().trim();
                }
            } else if (i == 11) {
                homeActivity.newPositionSpinner.setEnabled(false);
                urlParameters += "&" + parameterFields[i] + "=" + homeActivity.newPositionSpinner.getSelectedItem();
            } else {
                homeActivity.scrollableEditTextArray[i - 12].setEnabled(false);
                urlParameters += "&" + parameterFields[i] + "=" + homeActivity.scrollableEditTextArray[i - 12].getText().toString().trim().replaceAll("\\n", "@");
            }
        }
        String response = homeActivity.helper.postMethod(url, urlParameters);
        return response;
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        double salary, placementFee;
        if (homeActivity.editTextArray[4].getText().toString().trim().length() == 0) {
            salary = 0;
        } else {
            salary = Double.parseDouble(homeActivity.editTextArray[4].getText().toString().trim().replace(",", "."));
        }
        if (homeActivity.editTextArray[8].getText().toString().trim().length() == 0) {
            placementFee = 0;
        } else {
            placementFee = Double.parseDouble(homeActivity.editTextArray[8].getText().toString().trim().replace(",", "."));
        }
        homeActivity.removeSomeViews();
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        if (result.charAt(0) == 'A') {
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
            homeActivity.toastMessage.setText(result);
            homeActivity.toast.setView(homeActivity.toastMessage);
            homeActivity.toast.show();
            homeActivity.jobOrderDetails(homeActivity.jobDetailsModel);
        } else {
            JobOrderModel temp = new JobOrderModel();
            temp.jobOrderId = homeActivity.jobDetailsModel.jobOrderId;
            temp.position = homeActivity.jobDetailsModel.position;
            temp.dateInserted = homeActivity.jobDetailsModel.dateInserted;
            temp.contactName = homeActivity.editTextArray[0].getText().toString().trim();
            temp.department = homeActivity.editTextArray[1].getText().toString().trim();
            temp.billingContact = homeActivity.editTextArray[2].getText().toString().trim();
            temp.phone = homeActivity.editTextArray[3].getText().toString().trim();
            temp.salary = salary;
            temp.startingDate = homeActivity.editTextArray[5].getText().toString().trim();
            temp.address = homeActivity.editTextArray[6].getText().toString().trim();
            temp.businessType = homeActivity.editTextArray[7].getText().toString().trim();
            temp.placementFee = placementFee;
            temp.placementDate = homeActivity.editTextArray[9].getText().toString().trim();
            temp.actualStartingDate = homeActivity.editTextArray[10].getText().toString().trim();
            temp.newPosition = homeActivity.newPositionSpinner.getSelectedItem().toString();
            temp.educationalRequirements = homeActivity.scrollableEditTextArray[0].getText().toString().trim().replaceAll("\\n", "@");
            temp.experienceRequirements = homeActivity.scrollableEditTextArray[1].getText().toString().trim().replaceAll("\\n", "@");
            temp.duties = homeActivity.scrollableEditTextArray[2].getText().toString().trim().replaceAll("\\n", "@");
            temp.bonuses = homeActivity.scrollableEditTextArray[3].getText().toString().trim().replaceAll("\\n", "@");
            temp.travelRequirements = homeActivity.scrollableEditTextArray[4].getText().toString().replaceAll("\\n", "@");
            temp.car = homeActivity.scrollableEditTextArray[5].getText().toString().trim().replaceAll("\\n", "@");
            temp.careerOpportunities = homeActivity.scrollableEditTextArray[6].getText().toString().trim().replaceAll("\\n", "@");
            temp.interview = homeActivity.scrollableEditTextArray[7].getText().toString().trim().replaceAll("\\n", "@");
            temp.orderTaker = homeActivity.scrollableEditTextArray[8].getText().toString().trim().replaceAll("\\n", "@");
            temp.counselorUltimate = homeActivity.scrollableEditTextArray[9].getText().toString().trim().replaceAll("\\n", "@");
            temp.invoiceNo = homeActivity.jobDetailsModel.invoiceNo;
            int index = homeActivity.helper.getJobModelIndex(homeActivity.jobDetailsModel.jobOrderId, homeActivity.jobOrderModel);
            homeActivity.jobOrderModel.set(index, temp);
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#038E18"));
            homeActivity.toastMessage.setText(result);
            homeActivity.toast.setView(homeActivity.toastMessage);
            homeActivity.toast.show();
            homeActivity.relativeLayout.addView(homeActivity.horizontalScrollView);
            homeActivity.horizontalRelativeLayout.addView(homeActivity.scrollViewTable);
            homeActivity.navigateMe();
        }
        super.onPostExecute(result);
    }
}

class Delete extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {
        homeActivity.removeSomeViews();
        homeActivity.relativeLayout.addView(homeActivity.progressBar);
        homeActivity.progressBar.setVisibility(View.VISIBLE);
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    protected String doInBackground(Void... arg0) {
        String url = homeActivity.helper.getDomainName(homeActivity) + "deleteCandidateFound.php";
        String urlParameters = "candidateId=" + homeActivity.candidateId + "&clientId=" + homeActivity.companyModel.id;
        String response = homeActivity.helper.postMethod(url, urlParameters);
        return response;
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        if (result.charAt(0) == 'A') {
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
        } else {
            int index = homeActivity.helper.getCandidateModelIndex(homeActivity.candidateId, homeActivity.candidatesModel);
            homeActivity.candidatesModel.remove(index);
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#038E18"));
        }
        homeActivity.toastMessage.setText(result);
        homeActivity.toast.setView(homeActivity.toastMessage);
        homeActivity.toast.show();
        homeActivity.menu.getItem(1).setTitle("Candidates Found (" + homeActivity.candidatesModel.size() + ")");
        homeActivity.removeSomeViews();
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        homeActivity.relativeLayout.addView(homeActivity.horizontalScrollView);
        homeActivity.horizontalRelativeLayout.addView(homeActivity.scrollViewTable);
        homeActivity.navigateMe();
        super.onPostExecute(result);
    }
}

class UpdatePersonalData extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {
        homeActivity.removeSomeViews();
        homeActivity.relativeLayout.addView(homeActivity.progressBar);
        homeActivity.progressBar.setVisibility(View.VISIBLE);
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    protected String doInBackground(Void... arg0) {
        String url, urlParameters;
        if (homeActivity.updateData == 1) {
            url = homeActivity.helper.getDomainName(homeActivity) + "updateClientPass.php";
            urlParameters = "id=" + homeActivity.companyModel.id + "&pass=" + homeActivity.editTextArray[0].getText().toString().trim();
        } else {
            url = homeActivity.helper.getDomainName(homeActivity) + "updateClientEmail.php";
            urlParameters = "id=" + homeActivity.companyModel.id + "&email=" + homeActivity.editTextArray[2].getText().toString().trim();
        }
        String response = homeActivity.helper.postMethod(url, urlParameters);
        return response;
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        if (result.charAt(0) == 'A' || result.charAt(0) == 'E') {
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
        } else {
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#038E18"));
            if (homeActivity.updateData == 1) {
                homeActivity.companyModel.pass = editTextArray[0].getText().toString();
            } else {
                homeActivity.companyModel.email = editTextArray[2].getText().toString();
            }
        }
        homeActivity.toastMessage.setText(result);
        homeActivity.toast.setView(homeActivity.toastMessage);
        homeActivity.toast.show();
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        homeActivity.removeSomeViews();
        homeActivity.relativeLayout.addView(homeActivity.horizontalScrollView);
        homeActivity.horizontalRelativeLayout.addView(homeActivity.scrollViewTable);
        homeActivity.changeData();
        super.onPostExecute(result);
    }
}

class Check extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {
        homeActivity.removeSomeViews();
        homeActivity.relativeLayout.addView(homeActivity.progressBar);
        homeActivity.progressBar.setVisibility(View.VISIBLE);
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    protected String doInBackground(Void... arg0) {
        String url = homeActivity.helper.getDomainName(homeActivity) + "checkCandidate.php";
        String urlParameters = "candidateId=" + homeActivity.candidateId + "&jobOrderId=" + homeActivity.jobOrderId + "&clientId=" + homeActivity.companyModel.id;
        String response = homeActivity.helper.postMethod(url, urlParameters);
        return response;
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        if (result.charAt(0) == 'A') {
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
        } else {
            int index = homeActivity.helper.getCandidateModelIndex(homeActivity.candidateId, homeActivity.candidatesModel);
            homeActivity.candidatesModel.remove(index);
            index = homeActivity.helper.getJobModelIndex(homeActivity.jobOrderId, homeActivity.jobOrderModel);
            homeActivity.jobOrderModel.remove(index);
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#038E18"));
        }
        homeActivity.toastMessage.setText(result);
        homeActivity.toast.setView(homeActivity.toastMessage);
        homeActivity.toast.show();
        homeActivity.menu.getItem(0).setTitle("Your Job Requests (" + homeActivity.jobOrderModel.size() + ")");
        homeActivity.menu.getItem(1).setTitle("Candidates Found (" + homeActivity.candidatesModel.size() + ")");
        homeActivity.removeSomeViews();
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        homeActivity.relativeLayout.addView(homeActivity.horizontalScrollView);
        homeActivity.horizontalRelativeLayout.addView(homeActivity.scrollViewTable);
        homeActivity.navigateMe();
        super.onPostExecute(result);
    }
}

class Cancel extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {
        homeActivity.removeSomeViews();
        homeActivity.relativeLayout.addView(homeActivity.progressBar);
        homeActivity.progressBar.setVisibility(View.VISIBLE);
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    protected String doInBackground(Void... arg0) {
        String url = homeActivity.helper.getDomainName(homeActivity) + "cancelJob.php";
        String urlParameters = "jobId=" + homeActivity.jobOrderId;
        String response = homeActivity.helper.postMethod(url, urlParameters);
        return response;
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        if (result.charAt(0) == 'A') {
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#B81102"));
        } else {
            int index = homeActivity.helper.getJobModelIndex(homeActivity.jobOrderId, homeActivity.jobOrderModel);
            homeActivity.jobOrderModel.remove(index);
            homeActivity.toastMessage.setBackgroundColor(Color.parseColor("#038E18"));
            homeActivity.menu.getItem(0).setTitle("Your Job Requests (" + homeActivity.jobOrderModel.size() + ")");
        }
        homeActivity.toastMessage.setText(result);
        homeActivity.toast.setView(homeActivity.toastMessage);
        homeActivity.toast.show();
        homeActivity.removeSomeViews();
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        if (result.charAt(0) == 'A' && homeActivity.fieldsLength > 0) {
            homeActivity.jobOrderDetails(homeActivity.jobDetailsModel);
        } else {
            homeActivity.relativeLayout.addView(homeActivity.horizontalScrollView);
            homeActivity.horizontalRelativeLayout.addView(homeActivity.scrollViewTable);
            homeActivity.navigateMe();
        }
        super.onPostExecute(result);
    }
}

class RefreshData extends AsyncTask<Void, Integer, String> {
    protected void onPreExecute() {
        homeActivity.removeSomeViews();
        homeActivity.relativeLayout.addView(homeActivity.progressBar);
        homeActivity.progressBar.setVisibility(View.VISIBLE);
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    protected String doInBackground(Void... arg0) {
        try {
            String urlString = homeActivity.helper.getDomainName(homeActivity) + "clientsJobOrders.php?id=" + homeActivity.companyModel.id;
            ArrayList<JobOrderModel> jobOrderRefreshedModel = homeActivity.helper.getJobOrders(urlString);
            urlString = homeActivity.helper.getDomainName(homeActivity) + "candidatesFound.php?id=" + homeActivity.companyModel.id;
            ArrayList<CandidateModel> candidatesRefreshedModel = homeActivity.helper.getCandidates(urlString);
            urlString = homeActivity.helper.getDomainName(homeActivity) + "candidatesResumes.php?id=" + homeActivity.companyModel.id;
            homeActivity.resumesModel = homeActivity.helper.getResumes(urlString);
            homeActivity.refreshedData = candidatesRefreshedModel.size() - homeActivity.candidatesModel.size();
            homeActivity.jobOrderModel = jobOrderRefreshedModel;
            homeActivity.candidatesModel = candidatesRefreshedModel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Done";
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        homeActivity.menu.getItem(0).setTitle("Your Job Requests (" + homeActivity.jobOrderModel.size() + ")");
        homeActivity.menu.getItem(1).setTitle("Candidates Found (" + homeActivity.candidatesModel.size() + ")");
        homeActivity.progressBar.setVisibility(View.INVISIBLE);
        homeActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        String msg = "";
        if (homeActivity.refreshedData == 0) {
            msg = "No new candidates found yet!";
        } else {
            msg = homeActivity.refreshedData + " new candidates found.\nCheck your email for further information.";
        }
        new AlertDialog.Builder(homeActivity)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
        homeActivity.relativeLayout.addView(homeActivity.horizontalScrollView);
        homeActivity.horizontalRelativeLayout.addView(homeActivity.scrollViewTable);
        homeActivity.navigateMe();
        super.onPostExecute(result);
    }
}
