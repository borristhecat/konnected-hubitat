/**
 *  Konnected Motion Sensor
 *
 *  Copyright 2018 Konnected Inc (https://konnected.io)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
  definition (name: "Konnected Motion Sensor", namespace: "konnected-io", author: "konnected.io", mnmn: "SmartThings", vid:"generic-motion") {
    capability "Motion Sensor"
    capability "Sensor"
  }

  preferences {
    input name: "normalState", type: "enum", title: "Normal State",
      options: ["Normally Closed", "Normally Open"],
      defaultValue: "Normally Closed",
      description: "Most motion sensors are Normally Closed (NC), meaning that the circuit opens when motion is detected. To reverse this logic, select Normally Open (NO)."
      input name: "txtEnable", type: "bool", title: "Enable descriptionText logging", defaultValue: true
      
  }

}

def isClosed() {
  normalState == "Normally Open" ? "active" : "inactive"
}

def isOpen() {
  normalState == "Normally Open" ? "inactive" : "active"
}

// Update state sent from parent app
def setStatus(state) {
  def stateValue = state == "1" ? isOpen() : isClosed()
  sendEvent(name: "motion", value: stateValue)
 if (txtEnable) log.info "$device.label is $stateValue"
}
