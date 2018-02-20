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

/*
 * PassThrough.java
 * Copyright (C) 2018 University of Waikato, Hamilton, NZ
 */

package adams.flow.transformer.mongodbcollectionupdate;

import adams.core.Utils;
import com.mongodb.client.MongoCollection;

/**
 * Dummy, performs no update.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class Drop
  extends AbstractMongoDbCollectionUpdate {

  private static final long serialVersionUID = 3771202579365692102L;

  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return "Drops the collection.";
  }

  /**
   * Updates the collection.
   *
   * @param coll	the collection to update
   * @return		null if successful, otherwise the error message
   */
  @Override
  protected String doUpdate(MongoCollection coll) {
    String	result;

    result = null;

    try {
      coll.drop();
    }
    catch (Exception e) {
      result = Utils.handleException(this, "Failed to drop collection!", e);
    }

    return result;
  }
}