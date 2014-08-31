/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * DL4JEvaluationValues.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package adams.flow.transformer;

import org.deeplearning4j.eval.Evaluation;

import adams.core.QuickInfoHelper;
import adams.data.spreadsheet.Row;
import adams.data.spreadsheet.SpreadSheet;
import adams.flow.core.Token;

/**
 <!-- globalinfo-start -->
 <!-- globalinfo-end -->
 *
 <!-- flow-summary-start -->
 <!-- flow-summary-end -->
 *
 <!-- options-start -->
 <!-- options-end -->
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DL4JEvaluationValues
  extends AbstractTransformer {

  /** for serialization. */
  private static final long serialVersionUID = -7213972179879592883L;

  /** whether to include the statistics string as well. */
  protected boolean m_IncludeStats;
  
  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return "Turns a " + Evaluation.class.getName() + " object into a spreadsheet, listing all the statistics.";
  }

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
	    "include-stats", "includeStats",
	    false);
  }

  /**
   * Sets whether to include textual stats.
   *
   * @param value	true if to include textual stats
   */
  public void setIncludeStats(boolean value) {
    m_IncludeStats = value;
    reset();
  }

  /**
   * Returns whether to include the textual stats.
   *
   * @return		true if to include stats string
   */
  public boolean getIncludeStats() {
    return m_IncludeStats;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String includeStatsTipText() {
    return "If enabled, the textual statistics get included as well.";
  }

  /**
   * Returns a quick info about the actor, which will be displayed in the GUI.
   *
   * @return		null if no info available, otherwise short string
   */
  @Override
  public String getQuickInfo() {
    String	result;

    result  = QuickInfoHelper.toString(this, "includeStats", m_IncludeStats, "incl stats");
    
    return result;
  }

  /**
   * Returns the class that the consumer accepts.
   * 
   * @return		the Class of objects that can be processed
   */
  @Override
  public Class[] accepts() {
    return new Class[]{Evaluation.class};
  }

  /**
   * Returns the class of objects that it generates.
   *
   * @return		the Class of the generated tokens
   */
  @Override
  public Class[] generates() {
    return new Class[]{SpreadSheet.class};
  }

  /**
   * Adds the numeric statistic.
   * 
   * @param sheet	the sheet to add it to
   * @param name	the name of the statistic
   * @param value	the value of the statistic
   */
  protected void addRow(SpreadSheet sheet, String name, double value) {
    Row 	row;
    
    row = sheet.addRow();
    row.addCell("N").setContent(name);
    row.addCell("V").setContent(value);
  }

  /**
   * Adds the string statistic.
   * 
   * @param sheet	the sheet to add it to
   * @param name	the name of the statistic
   * @param value	the value of the statistic
   */
  protected void addRow(SpreadSheet sheet, String name, String value) {
    Row 	row;
    
    row = sheet.addRow();
    row.addCell("N").setContent(name);
    row.addCell("V").setContent(value);
  }
  
  /**
   * Executes the flow item.
   *
   * @return		null if everything is fine, otherwise error message
   */
  @Override
  protected String doExecute() {
    Evaluation	eval;
    SpreadSheet stats;
    Row 	row;
    
    eval  = (Evaluation) m_InputToken.getPayload();
    stats = new SpreadSheet();

    // header
    row = stats.getHeaderRow();
    row.addCell("N").setContent("Name");
    row.addCell("V").setContent("Value");
    
    // data
    addRow(stats, "Accuracy", eval.accuracy());
    addRow(stats, "Positive", eval.positive());
    addRow(stats, "Negative", eval.negative());
    addRow(stats, "Precision", eval.precision());
    addRow(stats, "Recall", eval.recall());
    addRow(stats, "False positive", eval.falsePositive());
    addRow(stats, "True negatives", eval.trueNegatives());
    addRow(stats, "F1", eval.f1());
    if (m_IncludeStats)
      addRow(stats, "Stats", eval.stats());

    m_OutputToken = new Token(stats);
    
    return null;
  }
}