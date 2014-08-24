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
 * DBNBuilder.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package adams.data.ml;

import org.deeplearning4j.datasets.DataSet;
import org.deeplearning4j.dbn.DBN;
import org.deeplearning4j.dbn.DBN.Builder;

/**
 <!-- globalinfo-start -->
 <!-- globalinfo-end -->
 *
 <!-- options-start -->
 <!-- options-end -->
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DBNBuilder
  extends AbstractMultiLayerNetworkBuilder<DBN> {

  /** for serialization. */
  private static final long serialVersionUID = 8804661387146021377L;

  /** the K parameter. */
  protected int m_K;
  
  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return "Builder for Deep Belief Networks (DBN).";
  }

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
	    "k", "k",
	    1);
  }

  /**
   * Sets the K parameter.
   *
   * @param value	the K
   */
  public void setK(int value) {
    m_K = value;
    reset();
  }

  /**
   * Returns the K parameter.
   *
   * @return		the K
   */
  public int getK() {
    return m_K;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String KTipText() {
    return "The K parameter, how often contrastive convergence is run.";
  }

  /**
   * Generates a new builder instance.
   * 
   * @return		the builder instance
   */
  @Override
  protected Builder newBuilder() {
    return new Builder();
  }
  
  /**
   * Performs the actual training of the network.
   * 
   * @param network	the network to train
   * @param data	the data to train with
   * @return		the trained network
   */
  @Override
  protected DBN doTrainNetwork(DBN network, DataSet data) {
    network.setInput(data.getFeatureMatrix());
    network.pretrain(data.getFirst(), m_K, m_LearningRate, m_NumEpochs);
    network.finetune(data.getSecond(), m_FineTuneLearningRate, m_FineTuneNumEpochs);
    
    return network;
  }

  /**
   * Returns the class of network that gets generated by the builder.
   * 
   * @return		the network class
   */
  @Override
  public Class generates() {
    return DBN.class;
  }
}
