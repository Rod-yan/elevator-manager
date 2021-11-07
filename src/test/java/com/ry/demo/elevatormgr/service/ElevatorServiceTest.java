package com.ry.demo.elevatormgr.service;

import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import com.ry.demo.elevatormgr.AppConfig;
import com.ry.demo.elevatormgr.AppInitializer;
import com.ry.demo.elevatormgr.ElevatorManagerApplication;
import com.ry.demo.elevatormgr.dao.ElevatorDao;
import com.ry.demo.elevatormgr.error.AlarmMechanismException;
import com.ry.demo.elevatormgr.model.Elevator;
import com.ry.demo.elevatormgr.model.Floor;

@WebMvcTest(ElevatorService.class)
@ContextConfiguration(classes={ElevatorManagerApplication.class, AppInitializer.class, AppConfig.class})
class ElevatorServiceTest {

    @MockBean
    ElevatorDao dao;
    @SpyBean
    ElevatorService elevatorService;
    
    Elevator testElevator_1 = null;
    
    @BeforeEach
    public void setUp() {
    	testElevator_1 = new Elevator("public", 1000.0);
        
        HashMap<Floor, Boolean> publicFloorsMap = new HashMap<Floor, Boolean>();
    	publicFloorsMap.put(new Floor(-1), true);
    	int i = 0;
    	while (i <= 49) {
    		publicFloorsMap.put(new Floor(i++), false);
    	}
    	publicFloorsMap.put(new Floor(50), true);
    	testElevator_1.setAvailableFloors(publicFloorsMap);
    }
	
	@Test
	void testUpdateElevatorAlarmMechanismTurnsOn() throws Exception {
		testElevator_1.setCurrentWeight(1050.0);
		testElevator_1.setCurrentFloor(15);
		
		Mockito.when(dao.getElevatorByDenomination("public")).thenReturn(testElevator_1);
		
		Assertions.assertThrows(
			AlarmMechanismException.class, 
			() -> elevatorService.updateElevator(testElevator_1)
	    );
	}
}
