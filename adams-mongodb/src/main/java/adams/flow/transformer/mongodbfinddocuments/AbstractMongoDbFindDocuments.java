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
 * AbstractMongoDbFindDocuments.java
 * Copyright (C) 2018 University of Waikato, Hamilton, NZ
 */

package adams.flow.transformer.mongodbfinddocuments;

import adams.core.QuickInfoSupporter;
import adams.core.option.AbstractOptionHandler;
import adams.flow.core.Actor;
import adams.flow.core.FlowContextHandler;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Ancestor for MongoDB collection update schemes.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractMongoDbFindDocuments
  extends AbstractOptionHandler
  implements FlowContextHandler, QuickInfoSupporter {

  private static final long serialVersionUID = 4047550340981016283L;

  /** the context. */
  protected Actor m_FlowContext;

  /**
   * Sets the context.
   *
   * @param value	the context
   */
  @Override
  public void setFlowContext(Actor value) {
    m_FlowContext = value;
  }

  /**
   * Returns the context.
   *
   * @return		the context, null if none set
   */
  @Override
  public Actor getFlowContext() {
    return m_FlowContext;
  }

  /**
   * Returns a quick info about the object, which can be displayed in the GUI.
   * <br>
   * Default implementation returns null.
   *
   * @return		null if no info available, otherwise short string
   */
  @Override
  public String getQuickInfo() {
    return null;
  }

  /**
   * Hook method for checking the collection before updating it.
   *
   * @param coll	the collection to check
   * @return		null if successful, otherwise error message
   */
  protected String check(MongoCollection coll) {
    if (coll == null)
      return "No collection provided!";
    if (m_FlowContext == null)
      return "No flow context set!";
    return null;
  }

  /**
   * Filters the collection.
   *
   * @param coll	the collection to filter
   * @return		the documents matching the query
   */
  protected abstract FindIterable<Document> doFind(MongoCollection coll);

  /**
   * Filters the collection.
   *
   * @param coll	the collection to filter
   * @return		the documents matching the query
   * @throws IllegalStateException	if check fails
   * @see		#check(MongoCollection)
   */
  public FindIterable<Document> find(MongoCollection coll) {
    FindIterable<Document>	result;
    String			msg;

    msg = check(coll);
    if (msg == null)
      result = doFind(coll);
    else
      throw new IllegalStateException(msg);

    return result;
  }
}