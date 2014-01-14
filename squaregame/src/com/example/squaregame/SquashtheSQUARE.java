package com.example.squaregame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SquashtheSQUARE extends Activity
  implements View.OnClickListener
{
  Button startBtn;

  public void onClick(View paramView)
  {
    if (paramView == this.startBtn)
      startActivity(new Intent(this, MainActivity.class));
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.squashthesquare);
    this.startBtn = ((Button)findViewById(R.id.startBtn));
    this.startBtn.setOnClickListener(this);
  }
}