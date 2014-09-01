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
 * AutoEncoderBuilder.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package adams.data.ml;

import org.deeplearning4j.autoencoder.AutoEncoder;
import org.deeplearning4j.autoencoder.AutoEncoder.Builder;
import org.deeplearning4j.datasets.DataSet;
import org.deeplearning4j.nn.NeuralNetwork;
import org.deeplearning4j.nn.gradient.NeuralNetworkGradient;

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
public class AutoEncoderBuilder
  extends AbstractBaseNetworkBuilder<AutoEncoder> {

  /** for serialization. */
  private static final long serialVersionUID = 8804661387146021377L;

  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return "Builder for Autoencoder.";
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
  protected AutoEncoder doTrainNetwork(AutoEncoder network, DataSet data) {
    network.setInput(data.getFeatureMatrix());
    network.trainTillConvergence(data.getFeatureMatrix(), m_LearningRate, new Object[]{1, m_LearningRate, m_NumEpochs});  // TODO 1?
    
    return network;
  }
  
  /**
   * Returns the class of network that gets generated by the builder.
   * 
   * @return		the network class
   */
  @Override
  public Class generates() {
    return AutoEncoder.class;
  }
  
  /**
   * Returns the network gradient.
   * 
   * @param network	the network to extract the gradient from
   * @return		the gradient
   */
  @Override
  public NeuralNetworkGradient getGradient(NeuralNetwork network) {
    return network.getGradient(new Object[]{getLearningRate(), getNumEpochs()});
  }
}
