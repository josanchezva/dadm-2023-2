package co.edu.unal.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

public class AndroidTicTacToeActivity extends Activity {
    public TicTacToeGame mGame;

    // Buttons making up the board
    public Button mBoardButtons[];
    // Various text displayed
    public TextView mInfoTextView;
    public Button button;
    public Button button2;
    public Button button3;
    public boolean mGameOver;
    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mBoardButtons = new Button[9];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);
        mInfoTextView = (TextView) findViewById(R.id.information);
        button = (Button) findViewById(R.id.new_game);
        button2 = (Button) findViewById(R.id.ai_difficulty);
        button3 = (Button) findViewById(R.id.quit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                startNewGame();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                showDialog(DIALOG_DIFFICULTY_ID);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                showDialog(DIALOG_QUIT_ID);
            }
        });
        mGame = new TicTacToeGame();
        mGameOver = false;

        startNewGame();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.new_game) {
            startNewGame();
            return true;
        } else if (itemId == R.id.ai_difficulty) {
            showDialog(DIALOG_DIFFICULTY_ID);
            return true;
        } else if (itemId == R.id.quit) {
            showDialog(DIALOG_QUIT_ID);
            return true;
        }
        return false;
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }*/

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
            case DIALOG_DIFFICULTY_ID:
                builder.setTitle(R.string.difficulty_choose);
                final CharSequence[] levels = {
                        getResources().getString(R.string.difficulty_easy),
                        getResources().getString(R.string.difficulty_harder),
                        getResources().getString(R.string.difficulty_expert)};
// TODO: Set selected, an integer (0 to n-1), for the Difficulty dialog.
// selected is the radio button that should be selected.
                int selected = 0;

                switch (mGame.getDifficultyLevel()) {
                    case Easy:
                        selected = 0;
                        break;
                    case Harder:
                        selected = 1;
                        break;
                    case Expert:
                        selected = 2;
                        break;
                }
                builder.setSingleChoiceItems(levels, selected,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                dialog.dismiss(); // Close dialog
// TODO: Set the diff level of mGame based on which item was selected.
                                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.values()[item]);
// Display the selected difficulty level
                                Toast.makeText(getApplicationContext(), levels[item],

                                        Toast.LENGTH_SHORT).show();

                            }
                        });
                dialog = builder.create();
                break;
            case DIALOG_QUIT_ID:
// Create the quit confirmation dialog
                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AndroidTicTacToeActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();
                break;
        }
        return dialog;
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("FUNCIONA HPTA", "HPTAAAAAAAAAA");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button, menu);
        //Log.d("FUNCIONA HPTA", "HPTAAAAAAAAAA");
        return true;
    }*/


    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startNewGame();
        return true;
    }*/

    private void startNewGame(){
        mGame.clearBoard();
        mGameOver = false;
        // Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        mInfoTextView.setText(R.string.first_human);;
    }
    public class ButtonClickListener implements View.OnClickListener{
        int location;
        public ButtonClickListener(int location) {
            this.location = location;
        }
        public void onClick(View view) {
            if(mGameOver == false){
                if (mBoardButtons[location].isEnabled()) {
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);
// If no winner yet, let the computer make a move
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mInfoTextView.setText(R.string.turn_computer);
                        int move = mGame.getComputerMove();
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }
                    if (winner == 0){
                        mInfoTextView.setText(R.string.turn_human);
                    }else if (winner == 1){
                        mInfoTextView.setText(R.string.result_tie);
                        mGameOver = true;
                    }else if (winner == 2){
                        mInfoTextView.setText(R.string.result_human_wins);
                        mGameOver = true;
                    }else{
                        mInfoTextView.setText(R.string.result_computer_wins);
                        mGameOver = true;
                    }
                }
            }
        }
    }

    public void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }
}
