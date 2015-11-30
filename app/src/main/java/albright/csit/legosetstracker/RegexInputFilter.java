/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  10.28.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.02.2015

   Assignment:  PizzaOrderAppFragments
    File Name:  RegexInputFilter.java
      Purpose:  To create regex filters for input fields.
                  Sub-static-classes to create filters for repeated use filters.
______________________________________________________________________________*/

package albright.csit.legosetstracker;

import android.text.InputFilter;
import android.text.Spanned;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexInputFilter implements InputFilter {

  ////  Fields
  /////////////////////////////
  private Pattern _pattern;

  ////  Constructor(s)
  /////////////////////////////
  public RegexInputFilter(String pattern){
    this(Pattern.compile(pattern));
  }

  public RegexInputFilter(Pattern pattern){
    if(pattern != null){
      _pattern = pattern;
    }
  }

  //  filter Function
  //
  //  Use:  Implementation of filter.
  //          Takes the _pattern and matches it to the destination plus source
  //          to see if it matches the pattern, or at least matches up to this
  //          point.
  //  Parameter(s):  CharSequence:source, int:srcStart, int:srcEnd,
  //                 Spanned:dest, int:destStart, int:destEnd
  //  Returns:  CharSequence
  //
  public CharSequence filter(CharSequence source, int srcStart, int srcEnd, Spanned dest, int destStart, int destEnd){
    String returnString = null;

    //  Builds the String that is intended to see if it matches the pattern.
    String tmp = dest.subSequence(0, destEnd).toString()        //  Get destination string from start to courser location
        + source.subSequence(srcStart,srcEnd)                   //  Get source string from start to end
        + dest.subSequence(destEnd, dest.length()).toString();  //  Get destination string from courser location to end of string.

    Matcher matcher = _pattern.matcher(tmp);

    if(!matcher.matches()){     //  If not matched
      if(!matcher.hitEnd()) {   //  If not to end of input
        returnString = "";
      }
    } else {
      returnString = null;
    }

    return returnString;
  }

  //  PhoneFilter static class
  //
  //  Use:  To filter input by a pre-formulated Phone number RegEx.
  //          So one does not need to keep submitting an expression each time.
  //  Valid Phone numbers will take these forms:
  //      1234567890
  //      123-456-7890
  //
  public static class PhoneFilter extends RegexInputFilter{
    /*==========================================================================
    My original Regex string for phone numbers that I created for CSIT 2230.
    I use www.regex101.com to help me create my regular expressions.
    Phone number can be in most any form.
    Examples:
    (123) 456-7890  <-- Parentheses are not needed
    1234567890      <-- Spaces are not needed
    123.456-7890    <-- space, dot, or dash can be used to separate groups
        456.7890    <-- Don't know the area code that's okay it will still work.
        "/^(?:(?:\(?([1-9][0-9]{2})\)?)[ .-]?)?([0-9]{3})[ .-]?([0-9]{4})$/"
    ==========================================================================*/

    ////  Phone Regex adapted from one I created for CSIT 2230.
    //  Phone Regex explained:  Must start at beginning of line '^'
    //                          Start of Capture '('
    //                          areacode '[1-9][0-9]{2}'
    //                          A hyphen can be here once or not at all '[-]?'
    //                          Next three digits of phone number '[0-9]{3}'
    //                          A hyphen can be here once or not at all '[-]?'
    //                          Last 4 digits '[0-9]{4})'
    //                          End Capture group ')'
    //                          Must be at end of line '$'
    //
    private static String  phoneNumber ="^([1-9][0-9]{2}[-]?[0-9]{3}[-]?[0-9]{4})$";

    ////  Constructor(s)
    /////////////////////////////
    public PhoneFilter() {
      super(phoneNumber);
    }
  }
}

