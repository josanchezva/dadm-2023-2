package co.edu.unal.tictactoe;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;

public class AndroidTicTacToeActivity extends Activity {
    public TicTacToeGame mGame;

    // Buttons making up the board
    public Button mBoardButtons[];
    // Various text displayed
    public TextView mInfoTextView;
    public Button button;
    public boolean mGameOver;
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
        button = (Button) findViewById(R.id.action_new_game);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                startNewGame();
            }
        });
        mGame = new TicTacToeGame();
        mGameOver = false;

        startNewGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("New Game");
        return true;
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("FUNCIONA HPTA", "HPTAAAAAAAAAA");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button, menu);
        //Log.d("FUNCIONA HPTA", "HPTAAAAAAAAAA");
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startNewGame();
        return true;
    }

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
