# =============================================================================
#      Copyright (c) 2017, NXP, All Rights Reserved.
#
#      Product:        Genesis
#                      World Wide Final Manufacturing CIM
#      Module:         2D
#      File:           2DBusinessRules.tcl
#
# =============================================================================
# Change History:
# Date          Author          Description
# May-25-2017   Kamal M Jan     Initial coding
# *****************************************************************************

proc TDSPicStartup {event {replybox {}}} {
   global tcl_version env MB_GROUP
   set ct 0
   set tdsgwConnected 0
   foreach row [mh_server_status ${tcl_version}] {
      if {${ct} == 0} {
         set ct 1
         continue
      }
      if {[lindex ${row} 0] eq "TDS_GW"} {
         if {[lindex ${row} end] ne {}} {
            set tdsgwConnected 1
         }
         break
      }
   }
   if {!${tdsgwConnected} && ![catch {exec ps -ex | grep \[T\]DSGateway | grep -w ${MB_GROUP} | awk \{print\ \$1\}} PID]} {
      exec kill ${PID}
   }
   if {[catch {exec ps -ex | grep \[T\]DSGateway | grep -w ${MB_GROUP}}]} {
      exec java -Dfile.encoding=UTF-8 -XX:+UseConcMarkSweepGC -XX:+CMSScavengeBeforeRemark -XX:+CMSParallelRemarkEnabled -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses -jar [file join $env(PIC_HOME) lib TDSGateway.jar] ${MB_GROUP} &
   }
}