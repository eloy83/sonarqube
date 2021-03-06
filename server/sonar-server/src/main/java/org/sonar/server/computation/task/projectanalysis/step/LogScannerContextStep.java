/*
 * SonarQube
 * Copyright (C) 2009-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.computation.task.projectanalysis.step;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.core.util.CloseableIterator;
import org.sonar.server.computation.task.projectanalysis.batch.BatchReportReader;
import org.sonar.server.computation.task.step.ComputationStep;

public class LogScannerContextStep implements ComputationStep {

  private static final Logger LOGGER = Loggers.get(LogScannerContextStep.class);

  private final BatchReportReader reportReader;

  public LogScannerContextStep(BatchReportReader reportReader) {
    this.reportReader = reportReader;
  }

  @Override
  public void execute() {
    CloseableIterator<String> logs = reportReader.readScannerLogs();
    try {
      while (logs.hasNext()) {
        LOGGER.info(logs.next());
      }
    } finally {
      logs.close();
    }
  }

  @Override
  public String getDescription() {
    return "Log scanner context";
  }
}
