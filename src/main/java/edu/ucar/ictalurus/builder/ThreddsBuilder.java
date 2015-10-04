/*
 * Copyright 1998-2009 University Corporation for Atmospheric Research/Unidata
 *
 * Portions of this software were developed by the Unidata Program at the
 * University Corporation for Atmospheric Research.
 *
 * Access and use of this software shall impose the following obligations
 * and understandings on the user. The user is granted the right, without
 * any fee or cost, to use, copy, modify, alter, enhance and distribute
 * this software, and any derivative works thereof, and its supporting
 * documentation for any purpose whatsoever, provided that this entire
 * notice appears in all copies of the software, derivative works and
 * supporting documentation.  Further, UCAR requests that the user credit
 * UCAR/Unidata in any publications that result from the use of this
 * software or in any product that includes this software. The names UCAR
 * and/or Unidata, however, may not be used in any advertising or publicity
 * to endorse or promote any products or commercial entity unless specific
 * written permission is obtained from UCAR/Unidata. The user also
 * understands that UCAR/Unidata is not obligated to provide the user with
 * any support, consulting, training or assistance of any kind with regard
 * to the use, operation and performance of this software nor to provide
 * the user with any updates, revisions, new versions or "bug fixes."
 *
 * THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package edu.ucar.ictalurus.builder;

/**
 * Parent type for all THREDDS catalog builders.
 *
 * @author edavis
 */
public interface ThreddsBuilder
{
  /**
   * {@link #isBuildable() ThreddsBuilder.isBuildable()} returns a
   * {@link Buildable Buildable} that indicates
   * whether the ThreddsBuilder can be built successfully or not. Whether a
   * ThreddsBuilder can be built successfully or not is only known once
   * {@link edu.ucar.ictalurus.builder.ThreddsBuilder#checkForIssues() ThreddsBuilder.checkForIssues()}
   * is called and before any further changes
   * are made to the ThreddsBuilder.
   */
  public enum Buildable {
    /**
     * The ThreddsBuilder that returned this Buildable has no FATAL
     * BuilderIssues and can therefore be built successfully.
     */
    YES,

    /**
     * The ThreddsBuilder that returned this Buildable has at least one FATAL
     * BuilderIssues which means it cannot be built successfully.
     *
     */
    NO,

    /**
     * The ThreddsBuilder that returned this Buildable has not been checked
     * since it was last changed. A call to checkForIssues() will determine if
     * it can be built successfully.
     */
    DONT_KNOW
  }

  /**
   * Check whether this ThreddsBuilder can be built successfully. Only when a
   * ThreddsBuilder has a FATAL BuilderIssue can it not be built successfully.
   *
   * @return a Buildable indicating whether this ThreddsBuilder can be built successfully.

   */
  public Buildable isBuildable();

  /**
   * Check whether the state of this ThreddsBuilder is such that build() will succeed.
   *
   * @return true if this ThreddsBuilder is in a state where build() will succeed.
   */
  public BuilderIssues checkForIssues();

  /**
   * Allow the addition of a BuilderIssue that represents an issue experienced by an external process
   * while in the process of constructing this ThreddsBuilder.
   *
   * @param issue the BuilderIssue to add to this ThreddsBuilder.
   */
  //public void addExternalIssue( BuilderIssue issue);

  /**
   * Allow the addition of BuilderIssues that represents a set of issues experienced by an external process
   * while in the process of constructing this ThreddsBuilder.
   *
   * @param issues the BuilderIssues to add to this ThreddsBuilder.
   */
  //public void addExternalIssues( BuilderIssues issues);

  /**
   * Generate the object being built by this ThreddsBuilder.
   *
   * @return the THREDDS catalog object being built by this ThreddsBuilder.
   * @throws IllegalStateException if this ThreddsBuilder is not in a valid state.
   */
  public Object build() throws IllegalStateException;

}
