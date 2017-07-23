/*
 * Copyright 2016 by Kappich Systemberatung Aachen
 * 
 * This file is part of de.bsvrz.sys.funclib.kappich.
 * 
 * de.bsvrz.sys.funclib.kappich is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * de.bsvrz.sys.funclib.kappich is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with de.bsvrz.sys.funclib.kappich; If not, see <http://www.gnu.org/licenses/>.

 * Contact Information:
 * Kappich Systemberatung
 * Martin-Luther-Straße 14
 * 52062 Aachen, Germany
 * phone: +49 241 4090 436 
 * mail: <info@kappich.de>
 */

package de.bsvrz.sys.funclib.kappich.filechooser;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author Kappich Systemberatung
 * @version $Revision$
 */
public interface FileChooser {

	void setCurrentDirectory(File file);

	void resetChoosableFileFilters();

	void setMultiSelectionEnabled(boolean b);

	void setFileSelectionMode(int fileSelectionMode);

	void addChoosableFileFilter(FileFilter fileFilter);

	void setFileFilter(FileFilter fileFilter);

	void setDialogTitle(String dialogTitle);

	File[] getSelectedFiles();

	File getSelectedFile();

	int showOpenDialog();

	int showSaveDialog();
}
