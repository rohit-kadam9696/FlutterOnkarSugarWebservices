package com.twd.flutter.android.bean;

import java.io.Serializable;

public class MainResponse implements Serializable {
    private boolean success;
    private boolean update;
    private ServerError se;
    private AppUpdate updateResponse;
    private String currentDateTime;
    private String locationName;
    

    private String vfullName, designation, slipboycode, mobileno, uniquestring, pggroups, yearCode, nuserRoleId, harvestingYearCode, perbygroup, allActiveYear,plantationRujwat,vrrtgYear,vfertYear;

    private String fromTimeRawana, toTimeRawana;
    private int nlocationId,nsugTypeId;


    public String getFromTimeRawana() {
        return fromTimeRawana;
    }

    public void setFromTimeRawana(String fromTimeRawana) {
        this.fromTimeRawana = fromTimeRawana;
    }

    public String getToTimeRawana() {
        return toTimeRawana;
    }

    public void setToTimeRawana(String toTimeRawana) {
        this.toTimeRawana = toTimeRawana;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ServerError getSe() {
        return se;
    }

    public void setSe(ServerError se) {
        this.se = se;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSlipboycode() {
        return slipboycode;
    }

    public void setSlipboycode(String slipboycode) {
        this.slipboycode = slipboycode;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getUniquestring() {
        return uniquestring;
    }

    public void setUniquestring(String uniquestring) {
        this.uniquestring = uniquestring;
    }

    public String getPggroups() {
        return pggroups;
    }

    public void setPggroups(String pggroups) {
        this.pggroups = pggroups;
    }
    public String getPerbygroup() {
        return perbygroup;
    }

    public void setPerbygroup(String perbygroup) {
        this.perbygroup = perbygroup;
    }

      public String getHarvestingYearCode() {
        return harvestingYearCode;
    }

    public void setHarvestingYearCode(String harvestingYearCode) {
        this.harvestingYearCode = harvestingYearCode;
    }

	public String getVfullName() {
		return vfullName;
	}

	public void setVfullName(String vfullName) {
		this.vfullName = vfullName;
	}

	public String getYearCode() {
		return yearCode;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	public String getNuserRoleId() {
		return nuserRoleId;
	}

	public void setNuserRoleId(String nuserRoleId) {
		this.nuserRoleId = nuserRoleId;
	}

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public AppUpdate getUpdateResponse() {
        return updateResponse;
    }

    public void setUpdateResponse(AppUpdate updateResponse) {
        this.updateResponse = updateResponse;
    }

	public String getAllActiveYear() {
		return allActiveYear;
	}

	public void setAllActiveYear(String allActiveYear) {
		this.allActiveYear = allActiveYear;
	}

	public String getPlantationRujwat() {
		return plantationRujwat;
	}

	public void setPlantationRujwat(String plantationRujwat) {
		this.plantationRujwat = plantationRujwat;
	}

	
	public String getVrrtgYear() {
		return vrrtgYear;
	}

	public void setVrrtgYear(String vrrtgYear) {
		this.vrrtgYear = vrrtgYear;
	}

	public String getVfertYear() {
		return vfertYear;
	}

	public void setVfertYear(String vfertYear) {
		this.vfertYear = vfertYear;
	}

	public int getNlocationId() {
		return nlocationId;
	}

	public void setNlocationId(int nlocationId) {
		this.nlocationId = nlocationId;
	}

	public int getNsugTypeId() {
		return nsugTypeId;
	}

	public void setNsugTypeId(int nsugTypeId) {
		this.nsugTypeId = nsugTypeId;
	}

	public String getCurrentDateTime() {
		return currentDateTime;
	}

	public void setCurrentDateTime(String currentDateTime) {
		this.currentDateTime = currentDateTime;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Override
	public String toString() {
		return "MainResponse [success=" + success + ", update=" + update + ", se=" + se + ", updateResponse="
				+ updateResponse + ", currentDateTime=" + currentDateTime + ", locationName=" + locationName
				+ ", vfullName=" + vfullName + ", designation=" + designation + ", slipboycode=" + slipboycode
				+ ", mobileno=" + mobileno + ", uniquestring=" + uniquestring + ", pggroups=" + pggroups + ", yearCode="
				+ yearCode + ", nuserRoleId=" + nuserRoleId + ", harvestingYearCode=" + harvestingYearCode
				+ ", perbygroup=" + perbygroup + ", allActiveYear=" + allActiveYear + ", plantationRujwat="
				+ plantationRujwat + ", vrrtgYear=" + vrrtgYear + ", vfertYear=" + vfertYear + ", fromTimeRawana="
				+ fromTimeRawana + ", toTimeRawana=" + toTimeRawana + ", nlocationId=" + nlocationId + ", nsugTypeId="
				+ nsugTypeId + "]";
	}
	
}
